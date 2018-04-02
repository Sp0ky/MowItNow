package com.xebia.mowitnow;

import java.util.Arrays;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.beans.MowerCoordinator;
import com.xebia.mowitnow.service.FileParserService;
import com.xebia.mowitnow.service.MowerCoordinatorService;


@SpringBootApplication
public class MowitnowApplication implements ApplicationRunner {

	/** 
	 * Logger
	 * */
    private static final Logger logger = LoggerFactory.getLogger(MowitnowApplication.class);

    /** 
     * Default file name for execution
     * */
	private static final String DEFAULT_FILE_PATH = "mowitnow-basic-input.txt";
	
	/** 
	 * Constant for file path argument
	 * */
	public static final String FILE_PATH_ARGUMENT = "file.path";
	
	/** 
	 * Services
	 * */
	@Autowired
	FileParserService fileService;
	@Autowired
	MowerCoordinatorService mowerCoordinatorService;
	
	
	
	/**
	 * Spring boot launcher 
	 * */
	public static void main(String... args) {
		SpringApplication.run(MowitnowApplication.class, args);
	}
	
	


    /** 
     * Main methods
     * */
	@Override
	public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));

		/** 
		 * Init file Path
		 * */
        String filePath = getFilePath(args);
        logger.info("filePath for input datas : {}", filePath);
        
        /** 
         * Parse Input File and initilialize MowerCoordinator
         * */
        MowerCoordinator mowerCoordinator = fileService.initMowing(filePath);
        logger.debug("coordinator : {}", mowerCoordinator);
        
        /** 
         * Run mowitNow
         * */
        List<Mower> endMowers = mowerCoordinatorService.mowIt(mowerCoordinator);
        
        
        /** 
         * Display mower positions
         * */
        if(!CollectionUtils.isEmpty(endMowers)) {
        	endMowers.forEach( x -> {
        		logger.info(x.toStringFinalPosition());
        	});	
        }
	}




	/** 
	 * Initialize file path.
	 * If args is empty or not contains file.path argument => DEFAULT_FILE_PATH 
	 * */
	private String getFilePath(ApplicationArguments args) {
	        
	        if(args != null && !CollectionUtils.isEmpty(args.getOptionNames())
	        		&& args.getOptionNames().contains(FILE_PATH_ARGUMENT)) {
	        	
	        	return args.getOptionValues(FILE_PATH_ARGUMENT).stream().findFirst().get();
	        	
	        } else {
	        	
	        	return DEFAULT_FILE_PATH;
	        
	        }
	}
      
}
