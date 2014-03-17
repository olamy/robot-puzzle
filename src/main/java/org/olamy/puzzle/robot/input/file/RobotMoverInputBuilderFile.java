package org.olamy.puzzle.robot.input.file;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.olamy.puzzle.robot.OutOfTableException;
import org.olamy.puzzle.robot.RobotOrder;
import org.olamy.puzzle.robot.Table;
import org.olamy.puzzle.robot.UnknownOrientationException;
import org.olamy.puzzle.robot.input.InputValidator;
import org.olamy.puzzle.robot.input.RobotMoverInput;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;
import org.olamy.puzzle.robot.util.RobotOrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotMoverInputBuilderFile
    implements RobotMoverInputBuilder
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    private File ordersFile;

    private RobotMoverInput robotMoverInput;

    private InputValidator inputValidator;

    /**
     * with a default file content see file robot-order in unit tests
     *
     * @param robotMoverInputFile
     */
    @Inject
    public RobotMoverInputBuilderFile( @RobotMoverInputFile File robotMoverInputFile, InputValidator inputValidator )
    {
        this.inputValidator = inputValidator;
        if ( robotMoverInputFile == null )
        {
            throw new IllegalArgumentException( "file cannot be null " );
        }
        if ( !robotMoverInputFile.exists() )
        {
            throw new IllegalArgumentException( "file " + robotMoverInputFile.getPath() + " not exists " );
        }
        this.ordersFile = robotMoverInputFile;
        try
        {
            this.buildInput();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "error during reading file: " + this.ordersFile.getPath(), e );
        }
        catch ( NumberFormatException e )
        {
            throw new RuntimeException( "error during parsing content file: " + this.ordersFile.getPath(), e );
        }
        catch ( UnknownOrientationException e )
        {
            throw new RuntimeException( "error during parsing Orientation: " + e.getMessage(), e );
        }
        catch ( OutOfTableException e )
        {
            throw new RuntimeException( "out of table PLACE order: " + e.getMessage(), e );
        }
    }

    private void buildInput()
        throws IOException, NumberFormatException, UnknownOrientationException, OutOfTableException
    {
        log.debug( "buildInput from file {}", this.ordersFile.getPath() );
        FileInputStream fileInputStream = null;

        RobotOrder robotOrder = null;

        List<String> orders = new ArrayList<>();

        try
        {
            fileInputStream = new FileInputStream( this.ordersFile );

            robotMoverInput = new RobotMoverInput( Table.DEFAULT_TABLE );

            List<String> lines = IOUtils.readLines( fileInputStream );
            int lineIndex = 1;
            for ( String line : lines )
            {
                line = StringUtils.trim( line );
                if ( !inputValidator.validateInput( line ) )
                {
                    log.warn( "skip line entry '{}' as it's not a correct order" );
                    continue;
                }
                log.debug( "handle line content {}", line );
                // we could accept the user configuring the size of the table as first entry
                /*
                if ( lineIndex == 1 )
                {
                    robotMoverInput.setTable( RobotOrderUtils.buildTable( line ) );
                    log.info( "build  Table {}", robotMoverInput.getTable() );
                    lineIndex++;
                    continue;
                }
                */
                if ( lineIndex == 1 )
                {
                    robotOrder = RobotOrderUtils.buildRobotOrderStart( line, robotMoverInput.getTable() );
                    robotMoverInput.setRobotOrder( robotOrder );
                }
                else
                {
                    // add to the list
                    orders.add( line );
                }
                lineIndex++;

            }

            robotOrder.setOrders( orders );
        }
        catch ( FileNotFoundException e )
        {
            // cannot happened as we check this in the constructor
            // so ignore it
        }
        finally
        {
            IOUtils.closeQuietly( fileInputStream );
        }
    }

    @Override
    public RobotMoverInput getRobotMoverInput()
    {
        return robotMoverInput;
    }

}
