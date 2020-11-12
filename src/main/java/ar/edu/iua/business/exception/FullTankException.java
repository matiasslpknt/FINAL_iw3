package ar.edu.iua.business.exception;

public class FullTankException extends Exception {


	private static final long serialVersionUID = 931105484889458525L;

	public FullTankException() {
	}

	public FullTankException(String message) {
		super(message);
	}

	public FullTankException(Throwable cause) {
		super(cause);
	}

	public FullTankException(String message, Throwable cause) {
		super(message, cause);
	}

	public FullTankException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
