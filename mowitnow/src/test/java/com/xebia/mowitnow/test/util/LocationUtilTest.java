package com.xebia.mowitnow.test.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.exception.ConcurrentLocationAccessException;
import com.xebia.mowitnow.exception.OutOfGardenException;
import com.xebia.mowitnow.util.LocationUtil;

@RunWith(SpringRunner.class)
public class LocationUtilTest {

	
	
	Garden garden;
	
	
	
	@Before
	public void init() {
		this.garden = new Garden(5, 5);
	}
	
	
	
	/** 
	 * Test for checkValidPositionif everything is ok 
	 * @throws ConcurrentLocationAccessException 
	 * @throws OutOfGardenException 
	 * */
	@Test
	public void testCheckValidPositionOk() throws OutOfGardenException, ConcurrentLocationAccessException {
		LocationUtil.checkValidPosition(garden, new Location(1, 1));
	}
	
	/** 
	 * Test for checkValidPosition when OutOfGardenException 
	 * @throws ConcurrentLocationAccessException 
	 * @throws OutOfGardenException 
	 * */
	@Test(expected = OutOfGardenException.class)
	public void testCheckValidPositionOutOfGardenException() throws OutOfGardenException, ConcurrentLocationAccessException {
		LocationUtil.checkValidPosition(garden, new Location(6, 1));
	}
	
	/** 
	 * Test for checkValidPosition when ConcurrentLocationAccessException 
	 * @throws ConcurrentLocationAccessException 
	 * @throws OutOfGardenException 
	 * */
	@Test(expected = ConcurrentLocationAccessException.class)
	public void testCheckValidPositionConcurrentLocationAccessException() throws OutOfGardenException, ConcurrentLocationAccessException {
		garden.addObstacle(new Location(1,1));
		LocationUtil.checkValidPosition(garden, new Location(1, 1));
	}
}
