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

import org.apache.commons.lang.StringUtils;
import org.olamy.puzzle.robot.input.RobotMoverInput;
import org.olamy.puzzle.robot.listener.RobotMoveListener;
import org.olamy.puzzle.robot.listener.RobotMoveListenerProvider;
import org.olamy.puzzle.robot.util.RobotOrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
@Singleton
public class DefaultRobotMover
    implements RobotMover
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    private List<RobotMoveListener> listeners;

    @Inject
    public DefaultRobotMover( RobotMoveListenerProvider robotMoveListenerProvider )
    {
        this.listeners = robotMoveListenerProvider.get();
    }

    @Override
    public Robot handleMoves( RobotMoverInput robotMoverInput )
        throws InvalidInstruction
    {
        if ( robotMoverInput == null )
        {
            throw new IllegalArgumentException( "robotMoverInput cannot be null" );
        }

        RobotOrder robotOrder = robotMoverInput.getRobotOrder();

        Table table = robotMoverInput.getTable();

        Robot robot = new Robot( robotOrder.getStartPosition(), robotOrder.getStartOrientation() );

        try
        {
            applyOrders( robotOrder.getOrders(), robot, table );
        }
        catch ( UnknownOrientationException e )
        {
            // normally cannot happened because we have controlled input before but
            throw new InvalidInstruction( e );
        }

        return robot;
    }

    private void applyOrders( List<String> orders, Robot robot, Table table )
        throws UnknownOrientationException
    {
        for ( String order : orders )
        {
            Position previousPosition = new Position( robot.getPosition().getX(), robot.getPosition().getY() );
            Orientation previousOrientation = Orientation.build( robot.getOrientation().asString() );

            if ( applyOrder( robot, order, table ) )
            {
                Position newPosition = new Position( robot.getPosition().getX(), robot.getPosition().getY() );
                Orientation newOrientation = Orientation.build( robot.getOrientation().asString() );

                for ( RobotMoveListener listener : listeners )
                {
                    RobotMoveEvent robotMoveEvent = new RobotMoveEvent() //
                        .setPreviousPosition( previousPosition ) //
                        .setPreviousOrientation( previousOrientation ) //
                        .setPosition( newPosition ) //
                        .setOrientation( newOrientation ) //
                        .setOrder( order );
                    listener.move( robotMoveEvent );
                }
            }
            else
            {
                log.warn( "skip move order {} as out of the table", order );
            }


        }
    }

    /**
     * @param robot
     * @param order
     * @param table
     * @return true if the robot has moved or a report command
     * @throws UnknownOrientationException
     */
    protected boolean applyOrder( Robot robot, String order, Table table )
        throws UnknownOrientationException
    {
        log.debug( "move {} with order {}", robot, order );
        switch ( order )
        {
            case LEFT_COMMAND:
            case RIGHT_COMMAND:
                robot.setOrientation( Orientation.changeOrientation( robot.getOrientation(), order ) );
                return true;
            case MOVE_COMMAND:
                Position position = calculateNewPosition( robot.getPosition(), robot.getOrientation() );
                if ( !outOfTable( table, position ) )
                {
                    robot.setPosition( position );
                }
                return true;
            case REPORT_COMMAND:
                //log.info( "Current position: {},{},{}", //
                //          robot.getPosition().getX(), //
                //          robot.getPosition().getY(), //
                //          robot.getOrientation().asString() );
                // nothing as it's done by the move listener
                return true;
            default:
                // special case can be a PLACE command so not in the switch/case !!
                if ( StringUtils.startsWith( order, PLACE_COMMAND ) )
                {
                    RobotOrder robotOrder = RobotOrderUtils.buildRobotOrderStart( order );
                    robot.setOrientation( robotOrder.getStartOrientation() );
                    robot.setPosition( robotOrder.getStartPosition() );
                    return true;
                }
                log.warn( "ignore unknown command " + order );
                break;
        }
        return false;
    }

    private Position calculateNewPosition( Position position, Orientation orientation )
        throws UnknownOrientationException
    {
        switch ( orientation.asString() )
        {
            case Orientation.NORTH:
                return new Position( position.getX(), position.getY() + 1 );
            case Orientation.EAST:
                return new Position( position.getX() + 1, position.getY() );
            case Orientation.WEST:
                return new Position( position.getX() - 1, position.getY() );
            case Orientation.SOUTH:
                return new Position( position.getX(), position.getY() - 1 );
            default:
                throw new UnknownOrientationException(
                    "hey mate! orientation " + orientation.asString() + " unknown when calculating new Position" );
        }

    }

    private boolean outOfTable( Table table, Position position )
    {
        return position.getX() > table.getTableSize().getX() //
            || position.getY() > table.getTableSize().getY() //
            || position.getX() < 0//
            || position.getY() < 0;
    }

}
