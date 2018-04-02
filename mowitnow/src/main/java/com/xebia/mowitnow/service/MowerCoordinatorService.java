package com.xebia.mowitnow.service;

import java.util.List;

import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.beans.MowerCoordinator;

public interface MowerCoordinatorService {

	
	/** 
	 * Process mowing
	 * @param mowerCoordinator , coordinator between garden and mowers
	 * @return List<Mower> , final list for the mower (display requirement)
	 * */
    public List<Mower> mowIt(MowerCoordinator mowerCoordinator);
    
}
