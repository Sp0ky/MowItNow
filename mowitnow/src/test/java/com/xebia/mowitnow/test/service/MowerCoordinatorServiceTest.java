package com.xebia.mowitnow.test.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.beans.MowerCoordinator;
import com.xebia.mowitnow.enumeration.Direction;
import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.service.FileParserService;
import com.xebia.mowitnow.service.MowerCoordinatorService;
import com.xebia.mowitnow.service.MowerService;
import com.xebia.mowitnow.service.impl.FileParserServiceImpl;
import com.xebia.mowitnow.service.impl.MowerCoordinatorServiceImpl;
import com.xebia.mowitnow.service.impl.MowerServiceImpl;



@RunWith(SpringRunner.class)
public class MowerCoordinatorServiceTest {

	
	
	@TestConfiguration
	static class FileParserServiceTestContextConfiguration {

		@Bean
		public FileParserService fileService() {
			return new FileParserServiceImpl();
		}
	}
	
	
	MowerCoordinatorService mowerCoordinatorService;
	
	@Autowired
	FileParserService fileParserService;
	
	
	@Before
	public void init() {
		MowerService mowerService = new MowerServiceImpl();
		this.mowerCoordinatorService = new MowerCoordinatorServiceImpl();
		ReflectionTestUtils.setField(mowerCoordinatorService, "mowerService", mowerService);
	}
	
	
	/** 
	 * Test for basic inputs (initial requirements)
	 * @throws Exception 
	 * */
	//@Test
	public void testBasicRequirementsTest() throws Exception {
		MowerCoordinator coordinator = fileParserService.initMowing("mowitnow-basic-input.txt");
		final List<Mower> resultList = mowerCoordinatorService.mowIt(coordinator);
		
		/** 
		 * Initialize output
		 * */
		List<Instruction> firstInstructionList = Arrays.asList(Instruction.GAUCHE, Instruction.AVANCER,
				Instruction.GAUCHE, Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.GAUCHE,
				Instruction.AVANCER, Instruction.AVANCER);
		List<Instruction> secondInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.AVANCER,
				Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER,
				Instruction.DROITE, Instruction.DROITE, Instruction.AVANCER);
		
		
		// Checks
		Assert.assertEquals(resultList.size(), 2);
		
		// Mower 1 
		Assert.assertEquals(resultList.get(0), new Mower(new Location(1, 3), Direction.NORTH, firstInstructionList));
		
		// Mower 2
		Assert.assertEquals(resultList.get(1), new Mower(new Location(5, 1), Direction.EAST, secondInstructionList));

	}
	
	/** 
	 * Test for one mower
	 * @throws Exception 
	 * */
	//@Test
	public void testOneMower() throws Exception {
		MowerCoordinator coordinator = fileParserService.initMowing("mowitnow-one-mower.txt");
		final List<Mower> resultList = mowerCoordinatorService.mowIt(coordinator);
		
		List<Instruction> firstInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER,
				Instruction.GAUCHE, Instruction.AVANCER, Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.AVANCER);
		
		// Checks
		Assert.assertEquals(resultList.size(), 1);
		
		// Mower 1 
		Assert.assertEquals(resultList.get(0), new Mower(new Location(0, 8), Direction.WEST, firstInstructionList));
	}
	
	
	/** 
	 * Test for three mowers
	 * @throws Exception 
	 * */
	@Test
	public void testThreeMowers() throws Exception {
		MowerCoordinator coordinator = fileParserService.initMowing("mowitnow-three-mowers.txt");
		final List<Mower> resultList = mowerCoordinatorService.mowIt(coordinator);
		
		List<Instruction> firstInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER);

		List<Instruction> secondInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.DROITE, 
				Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.AVANCER);

		List<Instruction> thirdInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, 
				Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER, Instruction.AVANCER);
		
		// Checks
		Assert.assertEquals(resultList.size(), 3);
		
		// Mower 1 
		Assert.assertEquals(resultList.get(0), new Mower(new Location(0, 10), Direction.NORTH, firstInstructionList));
		
		// Mower 2 
		Assert.assertEquals(resultList.get(1), new Mower(new Location(10, 1), Direction.SOUTH, secondInstructionList));
		
		// Mower 3
		Assert.assertEquals(resultList.get(2), new Mower(new Location(10, 10), Direction.EAST, thirdInstructionList));
	}
}
