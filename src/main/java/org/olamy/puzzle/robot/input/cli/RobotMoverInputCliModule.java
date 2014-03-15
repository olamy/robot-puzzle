package org.olamy.puzzle.robot.input.cli;

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
import org.olamy.puzzle.robot.DefaultRobotMover;
import org.olamy.puzzle.robot.RobotMover;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;
import org.olamy.puzzle.robot.input.cli.RobotMoverInputBuilderCli;
import org.olamy.puzzle.robot.listener.DefaultRobotMoveListenerProvider;
import org.olamy.puzzle.robot.listener.RobotMoveListener;
import org.olamy.puzzle.robot.listener.RobotMoveListenerLogger;
import org.olamy.puzzle.robot.listener.RobotMoveListenerProvider;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotMoverInputCliModule
    extends AbstractModule
{

    public RobotMoverInputCliModule()
    {
        // no op
    }

    @Override
    protected void configure()
    {
        bind( RobotMover.class ).to( DefaultRobotMover.class );
        bind( RobotMoverInputBuilder.class ).to( RobotMoverInputBuilderCli.class );
        bind( RobotMoveListener.class ).to( RobotMoveListenerLogger.class );
        bind( RobotMoveListenerProvider.class ).to( DefaultRobotMoveListenerProvider.class );

    }
}
