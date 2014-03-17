package org.olamy.puzzle.robot.util;

import org.junit.Assert;
import org.junit.Test;
import org.olamy.puzzle.robot.Orientation;
import org.olamy.puzzle.robot.OutOfTableException;
import org.olamy.puzzle.robot.RobotOrder;
import org.olamy.puzzle.robot.Table;
import org.olamy.puzzle.robot.TableSize;

/**
 * @author Olivier Lamy
 */
public class RobotOrderUtilsTest
{
    @Test
    public void test_build_table()
        throws Exception
    {
        Table table = RobotOrderUtils.buildTable( "2,3" );
        Assert.assertNotNull( table.getTableSize() );
        Assert.assertEquals( 2, table.getTableSize().getX() );
        Assert.assertEquals( 3, table.getTableSize().getY() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_build_table_wrong_order()
        throws Exception
    {
        RobotOrderUtils.buildTable( "2 3" );
    }

    @Test
    public void test_build_correct_robot_order()
        throws Exception
    {
        RobotOrder robotOrder = RobotOrderUtils.buildRobotOrderStart( "PLACE 2,4,NORTH", Table.DEFAULT_TABLE );
        Assert.assertNotNull( robotOrder );
        Assert.assertNotNull( robotOrder.getStartOrientation() );
        Assert.assertEquals( Orientation.NORTH, robotOrder.getStartOrientation().asString() );
        Assert.assertNotNull( robotOrder.getStartPosition() );
        Assert.assertEquals( 2, robotOrder.getStartPosition().getX() );
        Assert.assertEquals( 4, robotOrder.getStartPosition().getY() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_build_correct_robot_order_wrong_order()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "PLACE 2,4,BEER", Table.DEFAULT_TABLE );
    }

    @Test( expected = OutOfTableException.class )
    public void test_build_robot_order_out_of_table()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "PLACE 6,4,NORTH", Table.DEFAULT_TABLE );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_build_robot_order_wrong_order_with_negative_entries()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "PLACE -1,4,NORTH", Table.DEFAULT_TABLE );
    }

    @Test( expected = IllegalArgumentException.class )
    public void test_build_robot_order_with_empty_entry()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "", Table.DEFAULT_TABLE );
    }

    @Test( expected = OutOfTableException.class )
    public void test_build_robot_order_out_of_table_with_custom_table()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "PLACE 10,8,NORTH", new Table( new TableSize( 11, 7 ) ) );
    }

    @Test
    public void test_build_robot_order_with_custom_table()
        throws Exception
    {
        RobotOrderUtils.buildRobotOrderStart( "PLACE 10,8,NORTH", new Table( new TableSize( 11, 15 ) ) );
    }

}
