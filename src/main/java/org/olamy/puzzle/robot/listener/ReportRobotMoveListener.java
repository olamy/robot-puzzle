package org.olamy.puzzle.robot.listener;

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

import org.olamy.puzzle.robot.Position;
import org.olamy.puzzle.robot.RobotMoveEvent;
import org.olamy.puzzle.robot.RobotMover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Olivier Lamy
 */
@Singleton
@Named( "report" )
public class ReportRobotMoveListener
    implements RobotMoveListener
{
    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public void move( RobotMoveEvent robotMoveEvent )
    {
        if ( robotMoveEvent.getOrder().equals( RobotMover.REPORT_COMMAND ) )
        {
            // Output: 3,3,NORTH

            Position position = robotMoveEvent.getPosition();
            logger.info( "Current position: {},{},{}", position.getX(), position.getY(),
                         robotMoveEvent.getOrientation().asString() );
        }
    }
}
