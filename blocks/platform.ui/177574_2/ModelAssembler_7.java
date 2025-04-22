
	@Reference
	void setLogger(LoggerFactory factory) {
		this.factory = factory;
		this.logger = factory.getLogger(getClass());
	}
