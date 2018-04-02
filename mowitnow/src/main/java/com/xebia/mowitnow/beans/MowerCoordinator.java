package com.xebia.mowitnow.beans;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class MowerCoordinator {
	
	/** 
	 * @param garden , Garden to mow
	 * */
	private Garden garden;
	
	/** 
	 * @param Collection<Mower> , list of mowers for the garden mowing
	 * */
	private List<Mower> mowers;

	/**
	 * @param garden , garden to mow
	 * @param mowers , mowers that will mow the garden
	 * @throws Exception , if garden or mowers params are unvailable
	 */
	public MowerCoordinator(Garden garden, List<Mower> mowers) throws Exception {

		// Check input parameters
		if(garden == null) {
			throw new Exception("Garden is mandatory for mowing process");
		} else if (mowers == null || CollectionUtils.isEmpty(mowers)) {
			throw new Exception("At least one mower is mandatory for mowing process");
		}
		
		this.garden = garden;
		this.mowers = mowers;
	}

	/**
	 * @return the garden
	 */
	public Garden getGarden() {
		return garden;
	}

	/**
	 * @param garden the garden to set
	 */
	public void setGarden(Garden garden) {
		this.garden = garden;
	}

	/**
	 * @return the mowers
	 */
	public List<Mower> getMowers() {
		return mowers;
	}

	/**
	 * @param mowers the mowers to set
	 */
	public void setMowers(List<Mower> mowers) {
		this.mowers = mowers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * hashCode method for MowerCoordinator
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((garden == null) ? 0 : garden.hashCode());
		result = prime * result + ((mowers == null) ? 0 : mowers.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * equals method for MowerCoordinator
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MowerCoordinator other = (MowerCoordinator) obj;
		if (garden == null) {
			if (other.garden != null)
				return false;
		} else if (!garden.equals(other.garden))
			return false;
		if (mowers == null) {
			if (other.mowers != null)
				return false;
		} else if (!mowers.equals(other.mowers))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * toString method for MowerCoordinator
	 */
	@Override
	public String toString() {
		return "MowerCoordinator [garden=" + garden + ", mowers=" + mowers + "]";
	}
	


}
