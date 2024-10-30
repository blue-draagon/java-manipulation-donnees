package module;

public class DatasetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatasetException(String message) {
		super(message);
	}

	public DatasetException(Throwable cause) {
		super(cause);
	}

	public DatasetException(String message, Throwable cause) {
		super(message, cause);
	}
}
