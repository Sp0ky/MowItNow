package com.xebia.mowitnow.test.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.xebia.mowitnow.beans.MowerCoordinator;
import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.enumeration.Direction;
import com.xebia.mowitnow.service.FileParserService;
import com.xebia.mowitnow.service.impl.FileParserServiceImpl;

import com.xebia.mowitnow.exception.FileParserException;

@RunWith(SpringRunner.class)
public class FileParserServiceTest {

	@TestConfiguration
	static class FileParserServiceTestContextConfiguration {

		@Bean
		public FileParserService fileService() {
			return new FileParserServiceImpl();
		}
	}

	@Autowired
	FileParserService service;

	/** Test covering the use case file exists on the disk */
	@Test
	public void testFileExistsOnDisk() throws Exception {

		/** Initialize fake File and add content */
		File fakeFile = getFakeFile("fileOnDisk", Arrays.asList("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();
		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());

		/** Checks for coordinator content */
		mergedChecksForBasicInputs(coordinator);

	}

	/** Test covering the use case file exists on classpath */
	@Test
	public void testFileExistsOnClasspath() throws Exception {
		String classpathFileName = "mowitnow-basic-input.txt";
		MowerCoordinator coordinator = service.initMowing(classpathFileName);
		/** Checks for coordinator content */
		mergedChecksForBasicInputs(coordinator);
	}

	/** Test to match the Exception thrown if input file name is empty */
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyFileName() throws Exception {
		MowerCoordinator coordinator = service.initMowing(StringUtils.EMPTY);
		Assert.assertNull(coordinator);
	}

	/** Test to match when the file doesn't exist , output must be null */
	@Test
	public void testDoesNotExistsEmptyCoordinator() throws Exception {
		String wrongFileName = "anything";
		MowerCoordinator coordinator = service.initMowing(wrongFileName);
		Assert.assertNull(coordinator);
	}

	/** Test to match IllegalArgumentException when file structure is incorrect */
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionWrongNumberLinesInFile() throws Exception {
		File fakeFile = getFakeFile("WrongNumberLinesInFile", Arrays.asList("5 2", "1 2 N", "GAGAGAGAA", "3 3 E"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);
	}

	/**
	 * Test to match NumberFormatException when the coordinates to apply to the
	 * garden are unparseable (too big for Integer)
	 */
	@Test(expected = NumberFormatException.class)
	public void testNumberFormatExceptionForGarden() throws Exception {

		/** Initialize fake File and add content */
		// Max For Integer => 2 147 483 647
		File fakeFile = getFakeFile("NumberFormatExceptionGarden",
				Arrays.asList("5 2147483648", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);

	}

	/**
	 * Test to match FileParserException when the coordinates to apply to the garden
	 * are unvailable and doesn't match the regular expression
	 */
	@Test(expected = FileParserException.class)
	public void testFileParserExceptionForGarden() throws Exception {

		/** Initialize fake File and add content */
		File fakeFile = getFakeFile("FileParserExceptionGarden",
				Arrays.asList("5 A", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);

	}

	/**
	 * Test to match FileParserException when the instructions list in unparseable
	 * and doesn't match the regular expression
	 */
	@Test(expected = FileParserException.class)
	public void testFileParserExceptionForInstructions() throws Exception {

		/** Initialize fake File and add content */
		File fakeFile = getFakeFile("FileParserExceptionInstructions",
				Arrays.asList("5 5", "1 2 N", "BBBBGAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);

	}

	/**
	 * Test to match NumberFormatException when the coordinates for a mower are
	 * unparseable (too big for Integer)
	 */
	@Test(expected = NumberFormatException.class)
	public void testNumberFormatExceptionForMower() throws Exception {

		/** Initialize fake File and add content */
		File fakeFile = getFakeFile("NumberFormatExceptionMower",
				Arrays.asList("5 5", "1 2147483648 N", "GAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);

	}

	/**
	 * Test to match FileParserException when a location line for a Mower doesn't
	 * match the regular expression
	 */
	@Test(expected = FileParserException.class)
	public void testFileParserExceptionForMower() throws Exception {

		/** Initialize fake File and add content */
		File fakeFile = getFakeFile("FileParserExceptionMower",
				Arrays.asList("5 5", "1 2 N 4", "GAGAGAGAA", "3 3 E", "AADAADADDA"));
		fakeFile.deleteOnExit();

		MowerCoordinator coordinator = service.initMowing(fakeFile.getPath());
		Assert.assertNull(coordinator);

	}

	/**
	 * Merge checks for the basic inputs
	 */
	private void mergedChecksForBasicInputs(MowerCoordinator coordinator) {

		/**
		 * Initialize temporary Instructions lists
		 */
		List<Instruction> primaryInstructionList = Arrays.asList(Instruction.GAUCHE, Instruction.AVANCER,
				Instruction.GAUCHE, Instruction.AVANCER, Instruction.GAUCHE, Instruction.AVANCER, Instruction.GAUCHE,
				Instruction.AVANCER, Instruction.AVANCER);
		List<Instruction> secondaryInstructionList = Arrays.asList(Instruction.AVANCER, Instruction.AVANCER,
				Instruction.DROITE, Instruction.AVANCER, Instruction.AVANCER, Instruction.DROITE, Instruction.AVANCER,
				Instruction.DROITE, Instruction.DROITE, Instruction.AVANCER);

		/** Checks For Coordinator */
		Assert.assertNotNull(coordinator);
		Assert.assertNotNull(coordinator.getGarden());
		Assert.assertNotNull(coordinator.getMowers());
		Assert.assertEquals(coordinator.getMowers().size(), 2);

		/** Checks For Garden */
		Assert.assertEquals(coordinator.getGarden().getHeight(), new Integer(5));
		Assert.assertEquals(coordinator.getGarden().getWidth(), new Integer(5));
		Assert.assertNotNull(coordinator.getGarden().getObstacles());
		Assert.assertEquals(coordinator.getGarden().getObstacles().size(), 2);

		/** Checks For Mowers */
		// Mower 1
		Assert.assertNotNull(coordinator.getMowers().get(0));
		Assert.assertEquals(coordinator.getMowers().get(0).getDirection(), Direction.NORTH);
		Assert.assertEquals(coordinator.getMowers().get(0).getLocation().getX(), new Integer(1));
		Assert.assertEquals(coordinator.getMowers().get(0).getLocation().getY(), new Integer(2));
		Assert.assertEquals(coordinator.getMowers().get(0).getInstructions(), primaryInstructionList);
		// Mower 2
		Assert.assertNotNull(coordinator.getMowers().get(1));
		Assert.assertEquals(coordinator.getMowers().get(1).getDirection(), Direction.EAST);
		Assert.assertEquals(coordinator.getMowers().get(1).getLocation().getX(), new Integer(3));
		Assert.assertEquals(coordinator.getMowers().get(1).getLocation().getY(), new Integer(3));
		Assert.assertEquals(coordinator.getMowers().get(1).getInstructions(), secondaryInstructionList);
	}

	/**
	 * Util Method to generate a temp test file
	 * 
	 * @throws IOException
	 */
	private File getFakeFile(String fileName, Collection<String> lines) throws IOException {
		File fakeFile = File.createTempFile(fileName, ".txt");
		Collection<String> fakeLines = lines;
		FileWriter writer = new FileWriter(fakeFile);
		writer.write(StringUtils.join(fakeLines, '\n'));
		writer.flush();
		writer.close();
		return fakeFile;
	}
}
