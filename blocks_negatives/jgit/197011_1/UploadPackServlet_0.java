	private static int statusCodeForThrowable(Throwable error) {
		if (error instanceof ServiceNotEnabledException) {
			return SC_FORBIDDEN;
		}
		if (error instanceof WantNotValidException) {
			return SC_BAD_REQUEST;
		}
		return SC_INTERNAL_SERVER_ERROR;
	}

