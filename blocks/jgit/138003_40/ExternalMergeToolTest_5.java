		Set<String> expectedToolNames = new LinkedHashSet<>();
		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		for (CommandLineMergeTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedToolNames.add(toolName);
		}
		assertEquals("Incorrect set of external merge tools"
				actualToolNames);
	}

	@Test
	public void testOverridePredefinedToolPath() {
		String toolName = CommandLineMergeTool.guiffy.name();
		String customToolPath = "/usr/bin/echo";

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				"echo");
		config.setString(CONFIG_MERGETOOL_SECTION
				customToolPath);

		MergeTools manager = new MergeTools(db);
		Map<String
		ExternalMergeTool mergeTool = tools.get(toolName);
		assertNotNull("Expected tool \"" + toolName + "\" to be user defined"
				mergeTool);

		String toolPath = mergeTool.getPath();
		assertEquals("Expected external merge tool to have an overriden path"
				customToolPath
