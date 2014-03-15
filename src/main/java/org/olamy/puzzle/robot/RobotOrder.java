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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotOrder
{

    private Position startPosition;

    private Orientation startOrientation;

    private List<String> orders;

    public RobotOrder()
    {
        // no op
    }

    public RobotOrder( Position startPosition, Orientation startOrientation, List<String> orders )
    {
        this.startPosition = startPosition;
        this.startOrientation = startOrientation;
        this.orders = orders;
    }

    public Orientation getStartOrientation()
    {
        return startOrientation;
    }

    public List<String> getOrders()
    {
        if ( orders == null )
        {
            this.orders = new ArrayList<String>();
        }
        return orders;
    }

    public RobotOrder setStartOrientation( Orientation startOrientation )
    {
        this.startOrientation = startOrientation;
        return this;
    }

    public RobotOrder setOrders( List<String> orders )
    {
        this.orders = orders;
        return this;
    }

    public Position getStartPosition()
    {
        return startPosition;
    }

    public RobotOrder setStartPosition( Position startPosition )
    {
        this.startPosition = startPosition;
        return this;
    }

    @Override
    public String toString()
    {
        return "RobotOrder [startPosition=" + startPosition + ", startOrientation=" + startOrientation + ", orders="
            + orders + "]";
    }

}
