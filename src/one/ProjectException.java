package one;

/**
 * The Class ProjectException.
 */
public class ProjectException extends Exception {

	/**
 * Instantiates a new project exception.
 */
public ProjectException() {
		
	}

	/**
 * Instantiates a new project exception.
 *
 * @param message the message
 */
public ProjectException(String message) {
		super(message);
		
	}

	/**
 * Instantiates a new project exception.
 *
 * @param cause the cause
 */
public ProjectException(Throwable cause) {
		super(cause);

	}

	/**
 * Instantiates a new project exception.
 *
 * @param message the message
 * @param cause the cause
 */
public ProjectException(String message, Throwable cause) {
		super(message, cause);
		
	}
/* this method accepts a string message a throwable cause, boolean enableSuppression, boolean writableStacktrace 
 * as a parameter and returns a message
 */
	/**
 * Instantiates a new project exception.
 *
 * @param message the message
 * @param cause the cause
 * @param enableSuppression the enable suppression
 * @param writableStackTrace the writable stack trace
 */
public ProjectException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
