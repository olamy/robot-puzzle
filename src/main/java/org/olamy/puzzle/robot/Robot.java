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
public class Robot
{

    private Position position;

    private Orientation orientation;

    public Robot( Position position, Orientation orientation )
    {
        this.position = position;
        // TODO null check !
        this.orientation = orientation;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation( Orientation orientation )
    {
        this.orientation = orientation;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition( Position position )
    {
        this.position = position;
    }

    @Override
    public String toString()
    {
        return "Robot [position=" + position + ", orientation=" + orientation + "]";
    }
}
