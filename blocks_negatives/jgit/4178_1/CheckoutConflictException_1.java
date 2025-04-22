	private List<String> conflictingPaths;

	CheckoutConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	CheckoutConflictException(String message, List<String> conflictingPaths, Throwable cause) {
		super(message, cause);
		this.conflictingPaths = conflictingPaths;
	}

	CheckoutConflictException(String message) {
		super(message);
	}

	CheckoutConflictException(String message, List<String> conflictingPaths) {
		super(message);
		this.conflictingPaths = conflictingPaths;
	}
