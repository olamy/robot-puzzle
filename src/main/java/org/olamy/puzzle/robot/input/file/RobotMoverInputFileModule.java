package org.olamy.puzzle.robot.input.file;

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

import org.olamy.puzzle.robot.AbstractRobotMoverModule;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;

import java.io.File;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotMoverInputFileModule
    extends AbstractRobotMoverModule
{

    private File inputFile;

    public RobotMoverInputFileModule( File inputFile )
    {
        this.inputFile = inputFile;
    }

    @Override
    protected void configureInputModule()
    {
        bind( RobotMoverInputBuilder.class ).to( RobotMoverInputBuilderFile.class );
        bind( File.class ).annotatedWith( RobotMoverInputFile.class ).toInstance( this.inputFile );
    }
}
