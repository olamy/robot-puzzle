package org.olamy.puzzle.robot.input;

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
import org.olamy.puzzle.robot.RobotMover;
import org.olamy.puzzle.robot.util.RobotOrderUtils;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Olivier Lamy
 */
@Singleton
@Named( "default" )
public class DefaultInputValidator
    implements InputValidator
{
    private List<String> possibleOrders = Arrays.asList( RobotMover.LEFT_COMMAND, //
                                                         RobotMover.MOVE_COMMAND, //
                                                         RobotMover.RIGHT_COMMAND, //
                                                         RobotMover.REPORT_COMMAND );

    @Override
    public boolean validateInput( String order )
    {
        return StringUtils.isNotBlank( order ) && ( possibleOrders.contains( StringUtils.trim( order ) ) //
            || RobotOrderUtils.PLACE_ORDER_PATTERN.matcher( order ).matches() );
    }
}
