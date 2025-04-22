	protected void configureTools(
			BiConsumer<String, StoredConfig> configureDefaultTool,
			BiConsumer<String, StoredConfig> configureToolSubsection,
			String defaultToolName, String... extraToolNames) throws Exception {
		StoredConfig config = testRepository.getRepository().getConfig();
		config.clear();

		configureDefaultTool.accept(defaultToolName, config);

		List<String> toolNames = new ArrayList<>();
		toolNames.add(defaultToolName);
		if (extraToolNames != null) {
			toolNames.addAll(Arrays.asList(extraToolNames));
		}

		for (String toolName : toolNames) {
			configureToolSubsection.accept(toolName, config);
		}

		config.save();
	}

