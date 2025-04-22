		statusCode = HttpServletResponse.SC_FORBIDDEN;
	}

	public ServiceMayNotContinueException(String msg
		super(msg);
		this.statusCode = statusCode;
