	private static final long serialVersionUID = 1524499960923239786L;

	private final OperationErrorType type;

	/**
	 * General exception (no message).
	 */
	public OperationException() {
		super();
		type=OperationErrorType.GENERAL;
	}

	/**
	 * Exception with a message.
	 *
	 * @param eType the type of error that occurred
	 * @param msg the error message
	 */
	public OperationException(OperationErrorType eType, String msg) {
		super(msg);
		type=eType;
	}

	/**
	 * Get the type of error.
	 */
	public OperationErrorType getType() {
		return type;
	}

	@Override
	public String toString() {
		String rv=null;
		if(type == OperationErrorType.GENERAL) {
			rv="OperationException: " + type;
		} else {
			rv="OperationException: " + type + ": " + getMessage();
		}
		return rv;
	}
