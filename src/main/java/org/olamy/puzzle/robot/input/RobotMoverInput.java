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

import org.olamy.puzzle.robot.Position;
import org.olamy.puzzle.robot.RobotOrder;
import org.olamy.puzzle.robot.Table;
import org.olamy.puzzle.robot.TableSize;

/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
public class RobotMoverInput
{
    private Table table;

    private RobotOrder robotOrder;

    public RobotMoverInput()
    {
        // no op
    }

    public RobotMoverInput( Table table, RobotOrder robotOrder )
    {
        this.table = table;
        this.robotOrder = robotOrder;
    }

    public Table getTable()
    {
        if ( table == null )
        {
            // we use a default 5 x 5
            table = new Table( new TableSize( 5, 5 ) );
        }
        return table;
    }

    public RobotMoverInput setTable( Table table )
    {
        this.table = table;
        return this;
    }

    public RobotOrder getRobotOrder()
    {
        return robotOrder;
    }

    public RobotMoverInput setRobotOrder( RobotOrder robotOrder )
    {
        this.robotOrder = robotOrder;
        return this;
    }
}
