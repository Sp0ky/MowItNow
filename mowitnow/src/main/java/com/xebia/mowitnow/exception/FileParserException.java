package com.xebia.mowitnow.exception;

/** 
 * Exception thrown if data format is incorrect
 * during the data input file parsing phasis 
 * */
public class FileParserException extends Exception {



    
    /**
	 * @param serialVersionUID
	 */ 
	private static final long serialVersionUID = -5476927655950052507L;
	

	/** 
	 * @param message
	 * */
	public FileParserException(String mesage) {
        super(mesage);
    }
    
	/** 
	 * @param e , Throwable
	 * */
    public FileParserException(Throwable e) {
        super(e);
    }
    
    /** 
     * @param message
     *  @param e , Throwable
     * */
	public FileParserException(String mesage, Throwable e) {
        super(mesage, e);
    }
	
	
}
