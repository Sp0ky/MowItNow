package com.xebia.mowitnow.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum Direction {

    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");
    
	/** 
	 * Code of the Direction
	 * */
    private final String code;
    
    /**
     * @param code , code of the direction
     */
    private Direction(String code) {
        this.code = code;
    }

    /**
     * @param code 
     * @return Direction 
     */
    public static Direction getType(String code) {
        
    	if (StringUtils.isNotBlank(code)) {
    		
    		Direction direction = Arrays.asList(Direction.values()).stream().filter(x -> code.equals(x.getCode())).findFirst().get();
    		
    		if(direction == null) {
    			throw new IllegalArgumentException("Code : " + code + " is not a valid direction");
    		}
    		
    		return direction;
    		
        } else {
        	throw new IllegalArgumentException("Code for mower direction is empty");
        }       

    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    /** 
     * Relations between directions when moving to right 
     * */
    public static Direction moveToRight(Direction direction) {
    	Direction followingDirection = null;
    	if(direction != null) {
            switch(direction) {
                case NORTH : 
                	followingDirection = Direction.EAST;
                	break;
                case EAST  : 
                	followingDirection = Direction.SOUTH;
                	break;
                case SOUTH : 
                	followingDirection = Direction.WEST;
                	break;
                case WEST  : 
                	followingDirection = Direction.NORTH;
                	break;
                default    : ;
            }
        }
        
    	if(followingDirection == null) {
    		throw new IllegalArgumentException("Input direction doesn't match specifications : " + direction);
    	}
    	return followingDirection;
    }

    /** 
     * Relations between directions when moving to left 
     * */
    public static Direction moveToLeft(Direction direction) {
    	Direction followingDirection = null;
    	if(direction != null) {
        	switch(direction) {
                case NORTH : 
                	followingDirection =  Direction.WEST;
                	break;
                case WEST  : 
                	followingDirection = Direction.SOUTH;
                	break;
                case SOUTH : 
                	followingDirection =  Direction.EAST;
                	break;
                case EAST  : 
                	followingDirection = Direction.NORTH;
                	break;
                default : break;
            }
        } 
    	if(followingDirection == null) {
    		throw new IllegalArgumentException("Input direction doesn't match specifications : " + direction);
    	}
    	return followingDirection;
       
    }
}
