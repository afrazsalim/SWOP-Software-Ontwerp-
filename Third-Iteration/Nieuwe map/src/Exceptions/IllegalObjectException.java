package Exceptions;

public class IllegalObjectException extends RuntimeException {
	 public IllegalObjectException() {
         super();
     }
     public IllegalObjectException(String s) {
         super(s);
     }
     public IllegalObjectException(String s, Throwable throwable) {
         super(s, throwable);
     }
     public IllegalObjectException(Throwable throwable) {
         super(throwable);
     }

}
