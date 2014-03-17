package org.olamy.puzzle.robot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.olamy.puzzle.robot.input.RobotMoverInput;
import org.olamy.puzzle.robot.input.RobotMoverInputBuilder;
import org.olamy.puzzle.robot.input.file.RobotMoverInputBuilderFile;
import org.olamy.puzzle.robot.input.file.RobotMoverInputFileModule;

import java.io.File;
import java.util.Arrays;

public class MoverTest
{
    Injector injector;

    RobotMover robotMover;

    @Before
    public void initGuice()
    {
        injector =
            Guice.createInjector( new RobotMoverInputFileModule( new File( "./src/test/resources/robot-order.txt" ) ) );

        robotMover = injector.getInstance( RobotMover.class );
    }

    @Test
    public void check_robot_mover_input_builder_file()
        throws Exception
    {
        RobotMoverInputBuilder moverInputBuilder = injector.getInstance( RobotMoverInputBuilderFile.class );
        RobotMoverInput input = moverInputBuilder.getRobotMoverInput();
        Assert.assertNotNull( input );
        Assert.assertEquals( 5, input.getTable().getTableSize().getX() );
        Assert.assertEquals( 5, input.getTable().getTableSize().getY() );

        Assert.assertEquals( 5, input.getRobotOrder().getOrders().size() );

        RobotOrder order = input.getRobotOrder();
        Assert.assertEquals( 1, order.getStartPosition().getX() );
        Assert.assertEquals( 2, order.getStartPosition().getY() );
        Assert.assertEquals( Orientation.EAST, order.getStartOrientation().asString() );
        Assert.assertEquals( 5, order.getOrders().size() );

    }

    @Test
    public void robot_test_first_case()
        throws Exception
    {

        RobotOrder robotOrder = new RobotOrder() //
            .setStartPosition( new Position( 0, 0 ) ) //
            .setStartOrientation( Orientation.build( "NORTH" ) ) //
            .setOrders( Arrays.asList( "MOVE", "REPORT" ) );

        RobotMoverInput moverInput = new RobotMoverInput().setRobotOrder( robotOrder );

        Robot robot = robotMover.handleMoves( moverInput );
        Assert.assertNotNull( robot );

        Assert.assertEquals( 0, robot.getPosition().getX() );
        Assert.assertEquals( 1, robot.getPosition().getY() );
        // Output: 0,1,NORTH
        Assert.assertEquals( Orientation.NORTH, robot.getOrientation().asString() );

    }

    @Test
    public void robot_test_second_case()
        throws Exception
    {

        RobotOrder robotOrder = new RobotOrder() //
            .setStartPosition( new Position( 0, 0 ) ) //
            .setStartOrientation( Orientation.build( "NORTH" ) ) //
            .setOrders( Arrays.asList( "LEFT", "REPORT" ) );
        RobotMoverInput moverInput = new RobotMoverInput().setRobotOrder( robotOrder );

        Robot robot = robotMover.handleMoves( moverInput );
        Assert.assertNotNull( robot );

        Assert.assertEquals( 0, robot.getPosition().getX() );
        Assert.assertEquals( 0, robot.getPosition().getY() );
        Assert.assertEquals( Orientation.WEST, robot.getOrientation().asString() );
        //Output: 0,0,WEST

    }

    /**
     * assert move from input files
     */
    @Test
    public void robot_test_from_input_file()
        throws Exception
    {
        RobotMoverInputBuilder moverInputBuilder = injector.getInstance( RobotMoverInputBuilderFile.class );

        Robot robot = robotMover.handleMoves( moverInputBuilder.getRobotMoverInput() );
        Assert.assertNotNull( robot );

        Assert.assertEquals( 3, robot.getPosition().getX() );
        Assert.assertEquals( 3, robot.getPosition().getY() );
        Assert.assertEquals( Orientation.NORTH, robot.getOrientation().asString() );

    }

    /**
     * only out of table moves so no changes from initial position
     */
    @Test
    public void test_out_of_table_move_no_change()
        throws Exception
    {
        RobotOrder robotOrder = new RobotOrder() //
            .setStartPosition( new Position( 0, 0 ) ) //
            .setStartOrientation( Orientation.build( "SOUTH" ) ) //
            .setOrders( Arrays.asList( "MOVE", "MOVE", "REPORT" ) );
        RobotMoverInput moverInput = new RobotMoverInput().setRobotOrder( robotOrder );

        Robot robot = robotMover.handleMoves( moverInput );
        Assert.assertNotNull( robot );
        Assert.assertEquals( 0, robot.getPosition().getX() );
        Assert.assertEquals( 0, robot.getPosition().getY() );
        Assert.assertEquals( Orientation.SOUTH, robot.getOrientation().asString() );
    }

    /**
     * only out of table moves so no changes from initial position
     */
    @Test
    public void test_out_of_table_move_only_one_change_acccepted()
        throws Exception
    {
        RobotOrder robotOrder = new RobotOrder() //
            .setStartPosition( new Position( 0, 0 ) ) //
            .setStartOrientation( Orientation.build( "NORTH" ) ) //
            .setOrders( Arrays.asList( "MOVE", "LEFT", "MOVE", "REPORT" ) );
        RobotMoverInput moverInput = new RobotMoverInput().setRobotOrder( robotOrder );

        Robot robot = robotMover.handleMoves( moverInput );
        Assert.assertNotNull( robot );
        Assert.assertEquals( 0, robot.getPosition().getX() );
        Assert.assertEquals( 1, robot.getPosition().getY() );
        Assert.assertEquals( Orientation.WEST, robot.getOrientation().asString() );
    }

    @Test( expected = InvalidInstruction.class )
    public void test_invalid_instruction()
        throws Exception
    {
        RobotOrder robotOrder = new RobotOrder() //
            .setStartPosition( new Position( 0, 0 ) ) //
            .setStartOrientation( Orientation.build( "NORTH" ) ) //
            .setOrders( Arrays.asList( "SOMEWHERE", "REPORT" ) );
        RobotMoverInput moverInput = new RobotMoverInput().setRobotOrder( robotOrder );

        robotMover.handleMoves( moverInput );

    }

}
