package com.xebia.mowitnow.service;

import com.xebia.mowitnow.beans.Garden;
import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.enumeration.Instruction;

public interface MowerService {

	
	/** 
	 * General method to handle all moves in the {@link Garden} for a {@link Mower}
	 * The mower carries the list of instructions
	 * @param garden
	 * @param mower
	 * */
    public void move(Garden garden, Mower mower);
    
    
    /** 
     * Method used for each {@link Instruction} gave to the {@link Mower}
     * Handle one instruction for a mower in a garden, passed as parameters
     * @param garden
     * @param mower
     * @param instruction
     * 
     * */
    public void moveFromInstruction(Garden garden, Mower mower, Instruction instruction);
}
