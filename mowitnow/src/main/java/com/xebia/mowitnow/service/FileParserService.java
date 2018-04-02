package com.xebia.mowitnow.service;

import com.xebia.mowitnow.beans.MowerCoordinator;

public interface FileParserService {
	
    /**
     * Initialize mowing through input file
     * @param filePath , filePath to convert into input datas
     * @return return {@link MowerCoordinator} with garden and mowers
     * @throws Exception 
     */
	MowerCoordinator initMowing(final String filePath) throws Exception;

}
