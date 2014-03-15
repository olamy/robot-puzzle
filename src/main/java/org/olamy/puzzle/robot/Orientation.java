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


/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class Orientation
{

    public static final String NORTH = "NORTH";

    public static final String EAST = "EAST";

    public static final String WEST = "WEST";

    public static final String SOUTH = "SOUTH";

    public static final short NORTH_WEIGHT = 1;

    public static final short EAST_WEIGHT = 2;

    public static final short SOUTH_WEIGHT = 3;

    public static final short WEST_WEIGHT = 4;

    private String value;

    private short weight;

    protected Orientation( String value, short weight )
    {
        this.value = value;
        this.weight = weight;
    }

    public String asString()
    {
        return value;
    }

    public static Orientation changeOrientation( Orientation orientation, String command )
        throws UnknownOrientationException
    {
        int weight = 0;
        switch ( command )
        {
            case RobotMover.LEFT_COMMAND:
                if ( orientation.value == NORTH )
                {
                    weight = WEST_WEIGHT;
                }
                else
                {
                    weight = orientation.weight - 1;
                }
                break;
            case RobotMover.RIGHT_COMMAND:
                if ( orientation.value == SOUTH )
                {
                    weight = orientation.weight - 1;
                }
                else
                {
                    weight = orientation.weight + 1;
                }
                break;
            default:
                throw new UnknownOrientationException( "unknown orientation change command " + command );
        }

        switch ( weight )
        {
            case NORTH_WEIGHT:
                return new Orientation( NORTH, NORTH_WEIGHT );
            case EAST_WEIGHT:
                return new Orientation( EAST, EAST_WEIGHT );
            case 0:
            case 5:
            case WEST_WEIGHT:
                return new Orientation( WEST, WEST_WEIGHT );
            case SOUTH_WEIGHT:
                return new Orientation( SOUTH, SOUTH_WEIGHT );
            default:
                throw new UnknownOrientationException(
                    "error changing orientation " + orientation.toString() + " change command " + command );
        }

    }

    public static Orientation build( String letter )
        throws UnknownOrientationException
    {
        switch ( letter )
        {
            case NORTH:
                return new Orientation( letter, NORTH_WEIGHT );
            case EAST:
                return new Orientation( letter, EAST_WEIGHT );
            case WEST:
                return new Orientation( letter, WEST_WEIGHT );
            case SOUTH:
                return new Orientation( letter, SOUTH_WEIGHT );

        }
        // unknown orientation value !!
        throw new UnknownOrientationException( "unknown orientation value " + letter );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }
        if ( obj == this )
        {
            return true;
        }
        return this.getClass().cast( obj ).asString() == this.value;
    }

    @Override
    public String toString()
    {
        return "Orientation " + value;
    }


}
