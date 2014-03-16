package org.olamy.puzzle.robot;

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

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;
import org.olamy.puzzle.robot.input.cli.RobotMoverInputCliModule;
import org.olamy.puzzle.robot.input.RobotMoverInputException;
import org.olamy.puzzle.robot.input.file.RobotMoverInputFileModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 * @since 1.0
 */
public class RobotMoverCli
{

    public static void main( String[] args )
    {
        Logger logger = LoggerFactory.getLogger( RobotMoverCli.class );
        final RobotMoverCliParameters parameters = new RobotMoverCliParameters();
        JCommander jCommander = new JCommander( parameters );
        jCommander.parse( args );

        Injector injector = null;

        if ( parameters.commandFile == null )
        {
            injector = Guice.createInjector( new RobotMoverInputCliModule() );
        }
        else
        {
            final File file = new File( parameters.commandFile );
            if ( !file.exists() )
            {
                logger.info( "file {} not exists ", parameters.commandFile );
                System.exit( 1 );
            }
            injector = Guice.createInjector( new RobotMoverInputFileModule( file ) );
        }

        RobotMoverInputBuilder moverInputBuilder = injector.getInstance( RobotMoverInputBuilder.class );

        RobotMover robotMover = injector.getInstance( RobotMover.class );
        try
        {
            Robot robot = robotMover.handleMoves( moverInputBuilder.getRobotMoverInput() );
            logger.debug( "end handling moving" );
            logger.debug( "end positions {}", robot );
        }
        catch ( InvalidInstruction e )
        {
            logger.error( e.getMessage(), e );
            System.exit( 1 );
        }
        catch ( RobotMoverInputException e )
        {
            logger.error( e.getMessage(), e );
            System.exit( 1 );
        }
    }
}
