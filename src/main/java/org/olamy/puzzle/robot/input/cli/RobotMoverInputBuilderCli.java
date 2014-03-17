package org.olamy.puzzle.robot.input.cli;

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

import org.apache.commons.lang.SystemUtils;
import org.olamy.puzzle.robot.Position;
import org.olamy.puzzle.robot.RobotOrder;
import org.olamy.puzzle.robot.Table;
import org.olamy.puzzle.robot.TableSize;
import org.olamy.puzzle.robot.UnknownOrientationException;
import org.olamy.puzzle.robot.input.InputValidator;
import org.olamy.puzzle.robot.input.RobotMoverInput;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;
import org.olamy.puzzle.robot.input.RobotMoverInputException;
import org.olamy.puzzle.robot.util.RobotOrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.Console;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotMoverInputBuilderCli
    implements RobotMoverInputBuilder
{

    private InputValidator inputValidator;

    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    public RobotMoverInputBuilderCli( InputValidator inputValidator )
    {
        this.inputValidator = inputValidator;
    }

    // FIXME handle bad input
    @Override
    public RobotMoverInput getRobotMoverInput()
        throws RobotMoverInputException
    {
        try
        {
            Console console = System.console();
            PrintWriter writer = console.writer();
            writer.write( "Hello in robot mover, have Fun ! " + SystemUtils.LINE_SEPARATOR );
            // so we could accept the user to configure the size of the table
            /**
             writer.write( "Initial Table (sample 5 5) : " );
             console.flush();
             String tablePos = console.readLine();
             Table table = RobotOrderUtils.buildTable( tablePos );
             */
            RobotMoverInput moverInput = new RobotMoverInput();

            moverInput.setTable( new Table( new TableSize( 5, 5 ) ) );

            moverInput.setRobotOrder( getRobotOrder( console, writer ) );

            return moverInput;
        }
        catch ( Exception e )
        {
            throw new RobotMoverInputException( e.getMessage(), e );
        }
    }

    private RobotOrder getRobotOrder( Console console, PrintWriter writer )
        throws UnknownOrientationException
    {
        writer.write( "Robot start position and orientation (sample PLACE 1,2,EAST ) : " );
        console.flush();
        String robotStart = console.readLine();
        RobotOrder robotOrder = RobotOrderUtils.buildRobotOrderStart( robotStart );

        writer.write( "Robot orders (sample RIGHT, LEFT, MOVE, REPORT ) (end input with empty line) : "
                          + SystemUtils.LINE_SEPARATOR );
        console.flush();
        return robotOrder.setOrders( readOrders( console, writer ) );
    }

    private List<String> readOrders( Console console, PrintWriter writer )
    {
        List<String> orders = new ArrayList<>();
        String order = console.readLine();

        while ( order != null && !order.isEmpty() )
        {
            if ( !inputValidator.validateInput( order ) )
            {
                writer.write( "Entry not correct please use a correct format" );
                continue;
            }
            orders.add( order );
            order = console.readLine();
        }
        return orders;
    }
}
