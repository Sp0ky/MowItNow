package com.xebia.mowitnow.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/** 
 * List of available instructions
 * Instructions are in french to simplify specifiations matching
 * */
public enum Instruction {

	
    AVANCER("A"),
    DROITE("D"),
    GAUCHE("G");
    
	/** 
	 * Code of the instruction
	 * */
    private final String code;
    
    /**
     * @param code , code of the instructions
     */
    private Instruction(String code) {
        this.code = code;
    }

    /**
     * @param code 
     * @return Instruction
     */
    public static Instruction getType(String code) {
        
    	if (StringUtils.isNotBlank(code)) {
        	
    		Instruction instruction = Arrays.asList(Instruction.values()).stream().filter(x -> code.equals(x.getCode())).findFirst().get();
    		
    		if(instruction == null) {
    			throw new IllegalArgumentException("Code : " + code + " is not a valid instruction");
    		}
    		
    		return instruction;
        }
        
        return null;

    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
