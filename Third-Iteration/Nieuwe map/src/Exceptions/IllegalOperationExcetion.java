package Exceptions;

public class IllegalOperationExcetion extends RuntimeException {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 325723914834391776L;
	public IllegalOperationExcetion() {
         super();
     }
     public IllegalOperationExcetion(String s) {
         super(s);
     }
     public IllegalOperationExcetion(String s, Throwable throwable) {
         super(s, throwable);
     }
     public IllegalOperationExcetion(Throwable throwable) {
         super(throwable);
     }
}
