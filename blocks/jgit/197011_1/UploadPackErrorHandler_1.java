	public static int statusCodeForThrowable(Throwable error) {
		if (error instanceof ServiceNotEnabledException) {
			return SC_FORBIDDEN;
		}
		if (error instanceof PackProtocolException) {
			return SC_OK;
		}
		return SC_INTERNAL_SERVER_ERROR;
	}
