package org.olamy.puzzle.robot.util;

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
import org.olamy.puzzle.robot.Orientation;
import org.olamy.puzzle.robot.Position;
import org.olamy.puzzle.robot.RobotOrder;
import org.olamy.puzzle.robot.Table;
import org.olamy.puzzle.robot.TableSize;
import org.olamy.puzzle.robot.UnknownOrientationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotOrderUtils
{
    /**
     * pattern for PLACE order: PLACE x,y,NORTH/SOUTH/EAST/WEST
     */
    public static final Pattern PLACE_ORDER_PATTERN = Pattern.compile( "PLACE (\\d+),(\\d+),\\b(" //
                                                                           + Orientation.NORTH + //
                                                                           "|" + Orientation.SOUTH + //
                                                                           "|" + Orientation.EAST + //
                                                                           "|" + Orientation.WEST + //
                                                                           "\\b)" );

    private RobotOrderUtils()
    {
        // no op
    }

    public static Table buildTable( String line )
    {
        String[] tableStart = StringUtils.split( line, ' ' );

        // check tableStart length == 2
        if ( tableStart.length != 2 )
        {
            throw new IllegalArgumentException( "tableStart input " + line + " is not correct " );
        }
        return new Table( new TableSize( Short.valueOf( tableStart[0] ), Short.valueOf( tableStart[1] ) ) );
    }

    /**
     * @param line the order line
     * @return build the object considering and validating the received order is a PLACE (otherwise throw an {@link java.lang.IllegalArgumentException}
     * @throws UnknownOrientationException
     */
    public static RobotOrder buildRobotOrderStart( String line )
        throws UnknownOrientationException
    {
        // line content : PLACE x y orientation ( PLACE 0,0,NORTH )

        if ( line == null || line.isEmpty() )
        {
            throw new IllegalArgumentException( "robot InitialPosition input cannot be empty " );
        }

        Matcher matcher = PLACE_ORDER_PATTERN.matcher( line );
        if ( matcher.matches() )
        {
            return new RobotOrder().setStartPosition( //
                                                      new Position( Short.valueOf( matcher.group( 1 ) ).shortValue(), //
                                                                    Short.valueOf( matcher.group( 2 ) ).shortValue() )
            ) //
                .setStartOrientation( Orientation.build( matcher.group( 3 ) ) );
        }
        else
        {
            throw new IllegalArgumentException( "robot PLACE order '" + line + "' is not correct " );
        }


    }

}
