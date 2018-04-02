package com.xebia.mowitnow.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.enumeration.Direction;
import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.service.MowerService;
import com.xebia.mowitnow.service.impl.MowerServiceImpl;




@RunWith(SpringRunner.class)
public class MowerServiceTest {
    
	@TestConfiguration
    static class MowerServiceTestContextConfiguration {
  
        @Bean
        public MowerService mowerService() {
            return new MowerServiceImpl();
        }
    }
	
	@Autowired
	MowerService service;
	
	Garden garden;
	Mower mower;
	
	
	@Before
	public void init() {
		this.garden = new Garden(5, 5);
		this.mower = new Mower(new Location(1,1), null);
	}
	
	/** Test moveFromInstruction , case turnRight from North  */
	@Test
	public void testTurnRightFromNorth() {
		mower.setDirection(Direction.NORTH);
		service.moveFromInstruction(garden, mower, Instruction.DROITE);
		Assert.assertEquals(mower.getDirection(), Direction.EAST);
	}
	
	/** Test moveFromInstruction , case turnRight from South */
	@Test
	public void testTurnRightFromSouth() {
		mower.setDirection(Direction.SOUTH);
		service.moveFromInstruction(garden, mower, Instruction.DROITE);
		Assert.assertEquals(mower.getDirection(), Direction.WEST);
	}
	
	/** Test moveFromInstruction , case turnRight from East */
	@Test
	public void testTurnRightFromEast() {
		mower.setDirection(Direction.EAST);
		service.moveFromInstruction(garden, mower, Instruction.DROITE);
		Assert.assertEquals(mower.getDirection(), Direction.SOUTH);
	}
	
	/** Test moveFromInstruction , case turnRight from West */
	@Test
	public void testTurnRightFromWest() {
		mower.setDirection(Direction.WEST);
		service.moveFromInstruction(garden, mower, Instruction.DROITE);
		Assert.assertEquals(mower.getDirection(), Direction.NORTH);
	}
	
	/** Test moveFromInstruction , case turnLeft from North */
	@Test
	public void testTurnLeftFromNorth() {
		mower.setDirection(Direction.NORTH);
		service.moveFromInstruction(garden, mower, Instruction.GAUCHE);
		Assert.assertEquals(mower.getDirection(), Direction.WEST);
	}
	
	/** Test moveFromInstruction , case turnLeft from South */
	@Test
	public void testTurnLeftFromSouth() {
		mower.setDirection(Direction.SOUTH);
		service.moveFromInstruction(garden, mower, Instruction.GAUCHE);
		Assert.assertEquals(mower.getDirection(), Direction.EAST);
	}
	
	
	/** Test moveFromInstruction , case turnLeft from East */
	@Test
	public void testTurnLeftFromEast() {
		mower.setDirection(Direction.EAST);
		service.moveFromInstruction(garden, mower, Instruction.GAUCHE);
		Assert.assertEquals(mower.getDirection(), Direction.NORTH);
	}
	
	/** Test moveFromInstruction , case turnLeft from West */
	@Test
	public void testTurnLeftFromWest() {
		mower.setDirection(Direction.WEST);
		service.moveFromInstruction(garden, mower, Instruction.GAUCHE);
		Assert.assertEquals(mower.getDirection(), Direction.SOUTH);
	}
	
	/** Test moveFromInstruction , case moveAhead direction North */
	@Test
	public void testMoveAheadToNorth() {
		mower.setDirection(Direction.NORTH);
		service.moveFromInstruction(garden, mower, Instruction.AVANCER);
		Assert.assertEquals(mower.getLocation(), new Location(1, 2));
		Assert.assertEquals(garden.getObstacles().size(), 1);
		Assert.assertTrue(garden.getObstacles().contains(new Location(1, 2)));
	}
	
	/** Test moveFromInstruction , case moveAhead direction South */
	@Test
	public void testMoveAheadToSouth() {
		mower.setDirection(Direction.SOUTH);
		service.moveFromInstruction(garden, mower, Instruction.AVANCER);
		Assert.assertEquals(mower.getLocation(), new Location(1, 0));
		Assert.assertEquals(garden.getObstacles().size(), 1);
		Assert.assertTrue(garden.getObstacles().contains(new Location(1, 0)));
	}
	
	/** Test moveFromInstruction , case moveAhead direction East */
	@Test
	public void testMoveAheadToEast() {
		mower.setDirection(Direction.EAST);
		service.moveFromInstruction(garden, mower, Instruction.AVANCER);
		Assert.assertEquals(mower.getLocation(), new Location(2, 1));
		Assert.assertEquals(garden.getObstacles().size(), 1);
		Assert.assertTrue(garden.getObstacles().contains(new Location(2, 1)));
	}
	
	/** Test moveFromInstruction , case moveAhead direction West */
	@Test
	public void testMoveAheadToWest() {
		mower.setDirection(Direction.WEST);
		service.moveFromInstruction(garden, mower, Instruction.AVANCER);
		Assert.assertEquals(mower.getLocation(), new Location(0, 1));
		Assert.assertEquals(garden.getObstacles().size(), 1);
		Assert.assertTrue(garden.getObstacles().contains(new Location(0, 1)));
	}
	
}
