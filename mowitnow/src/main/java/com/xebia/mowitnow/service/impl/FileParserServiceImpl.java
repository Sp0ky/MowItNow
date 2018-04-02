package com.xebia.mowitnow.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.beans.MowerCoordinator;
import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.enumeration.Direction;
import com.xebia.mowitnow.exception.FileParserException;
import com.xebia.mowitnow.service.FileParserService;

@Service
public class FileParserServiceImpl implements FileParserService {

	/**
	 * @param logger
	 *            Logger for the class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileParserServiceImpl.class);

	/**
	 * @param GARDEN_REGEX
	 *            Regex for garden coords line
	 */
	private final static String GARDEN_REGEX = "^\\s*(\\d+)\\s+(\\d+)\\s*$";

	/**
	 * @param REGEX_MOWER
	 *            Regex for mower coords line
	 */
	private final static String MOWER_REGEX = "^\\s*(\\d+)\\s+(\\d+)\\s+([NESW])\\s*$";

	/**
	 * @param REGEX_MOVES
	 *            Regex for mower's moves lines
	 */
	private final static String INSTRUCTIONS_REGEX = "^\\s*([ADG]*)\\s*$";

	/**
	 * Initialize MowerCoordinator through file parsing
	 * 
	 * @param filePath
	 *            , path to input file
	 * @throws Exception
	 * @throws Il
	 */
	@Override
	public MowerCoordinator initMowing(String filePath) throws Exception {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("File Path is empty , impossible to initialize MowerCoordinator");
		}
		return parseInputFile(filePath);
	}

	/**
	 * Parse input file datas
	 * 
	 * @param filePath
	 *            , path to input file
	 * @throws Exception
	 */
	private MowerCoordinator parseInputFile(String filePath) throws Exception {

		logger.debug("Parsing file {} ", filePath);

		// Init file lines
		List<String> lines = readFile(filePath);

		// Check if exists lines if file
		if (!CollectionUtils.isEmpty(lines)) {
			logger.debug("File content : " + StringUtils.join(lines, "\r\n"));

			// Initialize Garden from the first line
			Garden garden = initializeGarden(lines.get(0));

			// Initialize mowers and commands from other lines
			List<Mower> mowers = initializeMowers(lines, garden);
			return new MowerCoordinator(garden, mowers);

		}
		return null;
	}

	/**
	 * Parse lines into mowers Mower is a combination of two line : initial position
	 * + Moves (ex : 1 1 N \r\n GAGDAGAD)
	 * 
	 * @param lines
	 *            , list of data input lines
	 * @param garden
	 *            , garden (only to add obstacles through mowers)
	 * @return List<Mower> , list of mowers with their commands
	 * @throws FileParserException 
	 * 
	 */
	private List<Mower> initializeMowers(List<String> lines, Garden garden) throws FileParserException {
		
		// Check if number of lines is coherent (number of line - 1 must be a even number)
		if((lines.size() - 1) % 2 != 0) {
			throw new IllegalArgumentException("Number of lines is not a even combination of position + commands for the mowers");
		}
		
		List<Mower> mowers = new ArrayList<Mower>();
		
		for (int i = 1; i < lines.size(); i+=2) {
			Mower mower = parseMower(lines.get(i));
			mower.setInstructions(parseInstructions(lines.get(i + 1)));
			mowers.add(mower);
			garden.addObstacle(mower.getLocation());
		}
		
		return mowers;
	}



	/**
	 * Parse mower line
	 * @param line , line to parse
	 * @return mower , mower after line parsing
	 * @throws FileParserException
	 */
	private Mower parseMower(String line) throws FileParserException {
		Pattern pattern = Pattern.compile(MOWER_REGEX);
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			try {
				Location mowerLocation = new Location(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
				Direction mowerDirection = Direction.getType(matcher.group(3));
				return new Mower(mowerLocation, mowerDirection);

			} catch (NumberFormatException e) {
				throw new NumberFormatException("Error while initializing mower Location " + e);
			}
		} else {
			throw new FileParserException("Unparseable data line for mower size, must match " + MOWER_REGEX);
		}
	}
	
	
	/** 
	 * @param line , line to parse
	 * @return List<Instruction>
	 * @throws FileParserException
	 * */
	private List<Instruction> parseInstructions(String line) throws FileParserException {
		Pattern pattern = Pattern.compile(INSTRUCTIONS_REGEX);
		Matcher matcher = pattern.matcher(line);
		
		if (matcher.find() && StringUtils.isNotBlank(matcher.group(1))) {
			String instructionsStr = matcher.group(1);
			return Arrays.asList(instructionsStr.trim().split("")).stream().map(x -> Instruction.getType(x)).collect(Collectors.toList());
		} else {
			throw new FileParserException("Unparseable data line for mower size, must match " + MOWER_REGEX);
		}
	}



	/**
	 * Parse the first line to initialize the Garden
	 * 
	 * @param line
	 *            , line to parse
	 * @return Garden , initialized Garden
	 * @throws FileParserException
	 */
	private Garden initializeGarden(String line) throws FileParserException {

		// Initialize pattern and matching
		Pattern pattern = Pattern.compile(GARDEN_REGEX);
		Matcher matcher = pattern.matcher(line);

		// Try to initialize garden
		if (matcher.find()) {
			try {
				int width = Integer.parseInt(matcher.group(1));
				int height = Integer.parseInt(matcher.group(2));
				return new Garden(width, height);

			} catch (NumberFormatException e) {
				throw new NumberFormatException("Error while parsing garden size" + e);
			}
		} else {
			throw new FileParserException("Unparseable data line for garden size, must match " + GARDEN_REGEX);
		}

	}

	/**
	 * Get file content as list of lines
	 * 
	 * @param filePath
	 *            , path to input file
	 * @return List<String> , lines of the file converted as list
	 */
	private List<String> readFile(String filePath) {
		try (InputStream is = getFileAsInputStream(filePath)) {
			if (is != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				return br.lines().collect(Collectors.toList());
			}
		} catch (IOException e) {
			logger.error("Error during file opening", e);
		}

		return null;
	}

	/**
	 * Check if the data file is present on the disk, or in the classpath 1st check
	 * on the disk , then classpath
	 * 
	 * @param filePath
	 *            , path to the data file
	 * @return , InputStream is , data file as stream
	 * @throws FileNotFoundException
	 */
	private InputStream getFileAsInputStream(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		if (file.exists()) {
			logger.debug("File is on the disk");
			return new FileInputStream(filePath);
		} else {
			logger.debug("File is in classpath");
			return FileParserServiceImpl.class.getClassLoader().getResourceAsStream(filePath);
		}
	}

}
