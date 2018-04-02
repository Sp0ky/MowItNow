package com.xebia.mowitnow.exception;


public class ConcurrentLocationAccessException extends Exception {
    
	/**
	 * @param serialVersionUID
	 */ 
	private static final long serialVersionUID = 9204588316189165347L;

	/** 
	 * @param message
	 * */
	public ConcurrentLocationAccessException(String mesage) {
        super(mesage);
    }
    
	/** 
	 * @param e , Throwable
	 * */
    public ConcurrentLocationAccessException(Throwable e) {
        super(e);
    }
    
    /** 
     * @param message
     *  @param e , Throwable
     * */
	public ConcurrentLocationAccessException(String mesage, Throwable e) {
        super(mesage, e);
    }
	
	
}
