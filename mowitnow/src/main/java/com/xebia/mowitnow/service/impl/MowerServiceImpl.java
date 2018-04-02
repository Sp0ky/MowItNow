package com.xebia.mowitnow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.service.MowerService;
import com.xebia.mowitnow.util.LocationUtil;

@Service
public class MowerServiceImpl implements MowerService {

	/** 
	 * Logger
	 * */
	private static final Logger logger = LoggerFactory.getLogger(MowerServiceImpl.class);

		
		
	   @Override
	    public void move(Garden garden, Mower mower) {
		   
		   // Check input parameters
	        if(mower != null && !CollectionUtils.isEmpty(mower.getInstructions())) {
	        	// Loop on instructions list
	        	mower.getInstructions().forEach( instruction -> {
	        			moveFromInstruction(garden, mower, instruction);
		                logger.debug("Mower new Location : " + mower.getLocation());
	        	});
	        }
	    }
	    
	    @Override
	    public void moveFromInstruction(Garden garden, Mower mower, Instruction instruction) {
	        
	    	// Check input parameters
	    	if(instruction != null && mower != null && garden != null) {
	            try {
	                switch(instruction) {
	                    case DROITE  : 
	                    	mower.turnRight();        
	                    	break;
	                    case GAUCHE  : 
	                    	mower.turnLeft();         
	                    	break;
	                    case AVANCER :
	                    	moveAhead(garden, mower); 
	                    	break;
	                    default : ;
	                }
	            }
	            catch(Exception e) {
	                logger.warn("Error while executing instruction => {} ", instruction.getCode(), e);
	            }
	        }
	    }
	    
	    /**
	     * Handle a move for a {@link Mower}
	     * First check if the next {@link Location} is available 
	     * An available Location is in the garden and is not an obstacle
	     * @param garden
	     * @param mower
	     */
	    private void moveAhead(Garden garden, Mower mower) throws Exception {
	    	
	    	// Check if the mower can reach the new location
	    	Location oldLocation = mower.getLocation();
	    	Location newLocation = mower.moveAheadAwaitedLocation();
	    	LocationUtil.checkValidPosition(garden, newLocation);

	        // Move the mower if the location is reachable
	        mower.moveAhead();
	        garden.updateObstacle(oldLocation, newLocation);
	    }

}
