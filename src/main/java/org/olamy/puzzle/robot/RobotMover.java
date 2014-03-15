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

import org.olamy.puzzle.robot.input.RobotMoverInput;


/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public interface RobotMover
{
    static final String RIGHT_COMMAND = "RIGHT";
    static final String LEFT_COMMAND = "LEFT";
    static final String MOVE_COMMAND = "MOVE";
    static final String REPORT_COMMAND = "REPORT";
    static final String PLACE_COMMAND = "PLACE";


    /**
     * @param robotMoverInput
     * @return List of final {@link Robot} positions respecting input order
     * in {@link org.olamy.puzzle.robot.input.RobotMoverInput#getRobotOrder()}
     * @throws InvalidInstruction
     */
    Robot handleMoves( RobotMoverInput robotMoverInput )
        throws InvalidInstruction;
}