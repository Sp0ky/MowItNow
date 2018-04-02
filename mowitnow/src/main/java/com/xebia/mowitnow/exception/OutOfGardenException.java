package com.xebia.mowitnow.exception;

public class OutOfGardenException extends Exception {

    
	/**
	 * @param serialVersionUID
	 */ 
	private static final long serialVersionUID = 7290429815631890358L;


	/** 
	 * @param message
	 * */
	public OutOfGardenException(String mesage) {
        super(mesage);
    }
    
	/** 
	 * @param e , Throwable
	 * */
    public OutOfGardenException(Throwable e) {
        super(e);
    }
    
    /** 
     * @param message
     *  @param e , Throwable
     * */
	public OutOfGardenException(String mesage, Throwable e) {
        super(mesage, e);
    }
	
	
}
