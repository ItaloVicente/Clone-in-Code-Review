	private IOException invalidHeader(Throwable cause) {
		IOException ioe = invalidHeader();
		ioe.initCause(cause);
		return ioe;
	}

