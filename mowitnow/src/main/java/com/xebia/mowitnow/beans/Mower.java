package com.xebia.mowitnow.beans;

import java.util.List;

import com.xebia.mowitnow.enumeration.Instruction;
import com.xebia.mowitnow.enumeration.Direction;

public class Mower {
	
    private Location location;
    private Direction direction;
    private List<Instruction> instructions;
	
    
    
    
    /**
	 * @param location
	 * @param direction
	 */
	public Mower(Location location, Direction direction) {
		super();
		this.location = location;
		this.direction = direction;
	}




	/**
	 * @param location
	 * @param direction
	 * @param instructions
	 */
	public Mower(Location location, Direction direction, List<Instruction> instructions) {
		super();
		this.location = location;
		this.direction = direction;
		this.instructions = instructions;
	}
	
	
	

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}




	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}



	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}




	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}




	/**
	 * @return the instructions
	 */
	public List<Instruction> getInstructions() {
		return instructions;
	}




	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instructions == null) ? 0 : instructions.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mower other = (Mower) obj;
		if (instructions == null) {
			if (other.instructions != null)
				return false;
		} else if (!instructions.equals(other.instructions))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (direction != other.direction)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mower [location=" + location + ", direction=" + direction + ", instructions=" + instructions + "]";
	}
	
	/** 
	 * Method to print final position for the mower
	 * */
	public String toStringFinalPosition() {
		return "Mower final location => Abscissa (X) = " + this.getLocation().getX() + ", Ordinate (Y) : " + this.getLocation().getY() + 
				", Direction : " + this.getDirection().getCode();
	}
    
	
	
    /** 
     * Handle a 90° move to right for the mower
     * */
    public void turnRight() {
        this.setDirection(Direction.moveToRight(direction));
    }
    
    /** 
     * Handle a 90° move to left for the mower
     * */
    public void turnLeft() {
    	this.setDirection(Direction.moveToLeft(direction));
    }
    
    
    /** 
     * Handle a move Ahead for the mower
     * */
    public void moveAhead() {
        Location newLocation = moveAheadAwaitedLocation();
        this.setLocation(newLocation);
    }

    /**
     * Get the expected {@link Location} after move forward
     * @return
     */
    public Location moveAheadAwaitedLocation() {
    	Location newLocation = new Location(this.location.getX(), this.location.getY());
        switch(direction) {
        	case NORTH : 
        		// Increment Y
        		newLocation.setY(newLocation.getY() + 1); 
        		break;
            case SOUTH : 
            	// Decrement Y
            	newLocation.setY(newLocation.getY() - 1);            	
            	break;
            case WEST  : 
            	// Decrement X
            	newLocation.setX(newLocation.getX() - 1);; 
            	break;
            case EAST  : 
            	// Increment X if moving east
            	newLocation.setX(newLocation.getX() + 1);; 
            	break;
            default : break ;

        }
        return newLocation;
    }
    
	

}
