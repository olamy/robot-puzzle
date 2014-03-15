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

import org.olamy.puzzle.robot.RobotMoveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;


/**
 * @author <a href="mailto:olamy@apache.org">Olivier Lamy</a>
 */
@Singleton
@Named( "logger" )
public class RobotMoveListenerLogger
    implements RobotMoveListener
{

    private Logger log = LoggerFactory.getLogger( getClass() );

    @Override
    public void move( RobotMoveEvent robotMoveEvent )
    {
        if ( robotMoveEvent == null )
        {
            return;
        }
        log.debug( "{}", robotMoveEvent );
    }

}
