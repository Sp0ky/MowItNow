package com.xebia.mowitnow.beans;

import java.util.HashSet;
import java.util.Set;

public class Garden {
	
	/** 
	 * height
	 * */
	private Integer height;
	
	/** 
	 * width
	 * */
	private Integer width;
	
	/** 
	 * Obstacles on the ground
	 * */
	private Set<Location> obstacles;

	/**
	 * @param height
	 * @param width
	 */
	public Garden(Integer width, Integer height) {
	
		if(height == null || width == null || height < 1 || width < 1) {
			throw new IllegalArgumentException(new StringBuilder("Wrong inputs while instantiating Garden, height : ")
				.append(height).append(" , width : ").append(width).toString());
		}
		this.height = height;
		this.width = width;
		
		// No obstacles by default
		this.obstacles = new HashSet<Location>();
	}

	/**
	 * @param height
	 * @param width
	 * @param obstacles
	 */
	public Garden(Integer height, Integer width, Set<Location> obstacles) {
		if(height == null || width == null || height < 1 || width < 1) {
			throw new IllegalArgumentException(new StringBuilder("Wrong inputs while instantiating Garden, height : ")
				.append(height).append(" , width : ").append(width).toString());
		}
		this.height = height;
		this.width = width;
		
		// Avoid NullPointerException if param obstacles is null
		this.obstacles = (obstacles != null ? obstacles : new HashSet<Location>());
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the obstacles
	 */
	public Set<Location> getObstacles() {
		return obstacles;
	}

	/**
	 * @param obstacles the obstacles to set
	 */
	public void setObstacles(Set<Location> obstacles) {
		this.obstacles = obstacles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((obstacles == null) ? 0 : obstacles.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		Garden other = (Garden) obj;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (obstacles == null) {
			if (other.obstacles != null)
				return false;
		} else if (!obstacles.equals(other.obstacles))
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Garden [height=" + height + ", width=" + width + ", obstacles=" + obstacles + "]";
	}

		
	/** 
	 * Check if the location is in the garden
	 * */
    public boolean isValid(Location position) {
        if(position != null) {
            return position.getX() >= 0 && position.getX() <= width && position.getY() >= 0 && position.getY() <= height;
        }
        return false;
    }
    
    /** 
     * Update obstacle location (update mower location on the garden)
     * */
    public void updateObstacle(Location oldLocation, Location newLocation) {
        removeObstacle(oldLocation);
        addObstacle(newLocation);
    }
    
    /** 
     * Add an obstacle in the garden
     * */
    public boolean addObstacle(Location location) {
        return obstacles.add(location);
    }
    
    /** 
     * Remove obstacle in the garden
     * */
    public boolean removeObstacle(Location location) {
        return obstacles.remove(location);
    }
    

}
