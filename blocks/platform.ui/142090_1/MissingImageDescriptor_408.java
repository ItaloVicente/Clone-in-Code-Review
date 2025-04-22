	static MissingImageDescriptor getInstance() {
		if (instance == null) {
			instance = new MissingImageDescriptor();
		}
		return instance;
	}
