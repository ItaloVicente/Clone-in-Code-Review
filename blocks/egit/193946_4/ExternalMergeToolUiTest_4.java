	private void configureEchoTools(String defaultToolName,
			String... extraToolNames) throws Exception {
		configureTools(this::configureDefaultTool,
				this::configureEchoToolSubsection, defaultToolName,
				extraToolNames);
	}

	private void configureDefaultTool(String toolName, StoredConfig config) {
