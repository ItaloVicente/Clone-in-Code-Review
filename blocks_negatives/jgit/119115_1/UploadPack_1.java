	/**
	 * Get the configured logger.
	 *
	 * @return the configured logger.
	 * @deprecated Use {@link #getPreUploadHook()}.
	 */
	@Deprecated
	public UploadPackLogger getLogger() {
		return logger;
	}

	/**
	 * Set the logger.
	 *
	 * @param logger
	 *            the logger instance. If null, no logging occurs.
	 * @deprecated Use {@link #setPreUploadHook(PreUploadHook)}.
	 */
	@Deprecated
	public void setLogger(UploadPackLogger logger) {
		this.logger = logger;
	}

