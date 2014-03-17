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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olivier Lamy
 */
public class DefaultInputValidatorTest
{
    private InputValidator inputValidator = new DefaultInputValidator();

    @Test
    public void validate_good_input()
        throws Exception
    {
        Assert.assertTrue( inputValidator.validateInput( "PLACE 1,1,EAST" ) );
        Assert.assertTrue( inputValidator.validateInput( "REPORT" ) );
        Assert.assertTrue( inputValidator.validateInput( "MOVE" ) );
        Assert.assertTrue( inputValidator.validateInput( "LEFT" ) );
        Assert.assertTrue( inputValidator.validateInput( "RIGHT" ) );
    }

    @Test
    public void not_validate_bad_input()
        throws Exception
    {
        Assert.assertFalse( inputValidator.validateInput( "PLACE 1" ) );
        Assert.assertFalse( inputValidator.validateInput( "REPORTS" ) );
        Assert.assertFalse( inputValidator.validateInput( "ONTHEMOVE" ) );
        // seriously both should/MUST be accepted!!
        Assert.assertFalse( inputValidator.validateInput( "BEER" ) );
        Assert.assertFalse( inputValidator.validateInput( "COFFEE" ) );
    }

}
