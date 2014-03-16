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

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.olamy.puzzle.robot.input.DefaultInputValidator;
import org.olamy.puzzle.robot.input.InputValidator;
import org.olamy.puzzle.robot.listener.DefaultRobotMoveListenerProvider;
import org.olamy.puzzle.robot.listener.ReportRobotMoveListener;
import org.olamy.puzzle.robot.listener.RobotMoveListener;
import org.olamy.puzzle.robot.listener.RobotMoveListenerLogger;
import org.olamy.puzzle.robot.listener.RobotMoveListenerProvider;

/**
 * @author Olivier Lamy
 */
public abstract class AbstractRobotMoverModule
    extends AbstractModule
{


    @Override
    protected void configure()
    {
        bind( RobotMover.class ).to( DefaultRobotMover.class );
        bind( InputValidator.class ).to( DefaultInputValidator.class );
        bind( RobotMoveListener.class ).annotatedWith( Names.named( "logger" ) ).to( RobotMoveListenerLogger.class );
        bind( RobotMoveListener.class ).annotatedWith( Names.named( "report" ) ).to( ReportRobotMoveListener.class );
        bind( RobotMoveListenerProvider.class ).to( DefaultRobotMoveListenerProvider.class );
        configureInputModule();

    }

    protected abstract void configureInputModule();

}
