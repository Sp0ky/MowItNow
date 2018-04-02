package com.xebia.mowitnow.util;

import java.util.Set;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Location;
import com.xebia.mowitnow.exception.ConcurrentLocationAccessException;
import com.xebia.mowitnow.exception.OutOfGardenException;

public class LocationUtil {
	
	/** 
	 * Private constructor
	 * Only used by static calls
	 * */
	private LocationUtil() {} ;
	

    
    /**
     * Make all checks to validate the next move
     * @param garden
     * @param location
     * @throws OutOfGardenException , if location is outside of the garden
     * @throws ConcurrentLocationAccessException , if another mower is already of the location
     */
    public static void checkValidPosition(Garden garden, Location location) throws OutOfGardenException, ConcurrentLocationAccessException {
        checkGardenLocation(garden, location);
        checkObstacles(garden, location);
    }
    
    /**
     * Check that the {@link Location} is available for the current {@link Garden}.
     * The Location is available if the coordinates are in the Garden
     * @param garden
     * @param location
     * @throws OutOfGardenException
     */
    private static void checkGardenLocation(Garden garden, Location location) throws OutOfGardenException {
        if(!garden.isValid(location)) {
            throw new OutOfGardenException("Location unvailable in the current garden, location : " + location + ", garden : " + garden);
        }
    }
    
    /**
     * Check that the {@link Location} is available for the current {@link Garden}.
     * The location is available if the coordinates doesn't match an obstacle
     * @param garden
     * @param location
     * @throws ConcurrentLocationAccessException
     */
    private static void checkObstacles(Garden garden, Location location) throws ConcurrentLocationAccessException {
        Set<Location> obstaclesLocations = garden.getObstacles();
        if(obstaclesLocations != null && obstaclesLocations.contains(location)) {
            throw new ConcurrentLocationAccessException("Location unvailable , an obstacle is present at this location : " + location);
        }
    }
}
