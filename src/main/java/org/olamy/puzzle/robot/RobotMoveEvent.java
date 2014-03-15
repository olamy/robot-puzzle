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
public class RobotMoveEvent
{
    private Position previousPosition;

    private Orientation previousOrientation;

    private Position position;

    private Orientation orientation;

    private String order;

    public RobotMoveEvent()
    {
        // no op
    }

    public Position getPreviousPosition()
    {
        return previousPosition;
    }

    public RobotMoveEvent setPreviousPosition( Position previousPosition )
    {
        this.previousPosition = previousPosition;
        return this;
    }

    public Orientation getPreviousOrientation()
    {
        return previousOrientation;
    }

    public RobotMoveEvent setPreviousOrientation( Orientation previousOrientation )
    {
        this.previousOrientation = previousOrientation;
        return this;
    }

    public Position getPosition()
    {
        return position;
    }

    public RobotMoveEvent setPosition( Position position )
    {
        this.position = position;
        return this;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public RobotMoveEvent setOrientation( Orientation orientation )
    {
        this.orientation = orientation;
        return this;
    }

    public String getOrder()
    {
        return order;
    }

    public RobotMoveEvent setOrder( String order )
    {
        this.order = order;
        return this;
    }

    @Override
    public String toString()
    {
        return "RobotMoveEvent [previousPosition=" + previousPosition + ", previousOrientation=" + previousOrientation
            + ", position=" + position + ", orientation=" + orientation + ", order=" + order + "]";
    }

}
