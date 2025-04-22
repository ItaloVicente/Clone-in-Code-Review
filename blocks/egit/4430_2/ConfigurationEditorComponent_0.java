		this.changeablePath = changeablePath;
	}

	void setConfig(FileBasedConfig config) throws IOException {
		editableConfig = config;
		restore();
