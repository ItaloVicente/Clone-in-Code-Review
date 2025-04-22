	@Test
	public void testOverridePredefinedToolPath() {
		String toolName = CommandLineDiffTool.guiffy.name();
		String customToolPath = "/usr/bin/echo";

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				"echo");
		config.setString(CONFIG_DIFFTOOL_SECTION
				customToolPath);

		DiffTools manager = new DiffTools(db);
		Map<String
		ExternalDiffTool diffTool = tools.get(toolName);
		assertNotNull("Expected tool \"" + toolName + "\" to be user defined"
				diffTool);

		String toolPath = diffTool.getPath();
		assertEquals("Expected external diff tool to have an overriden path"
				customToolPath
	}

