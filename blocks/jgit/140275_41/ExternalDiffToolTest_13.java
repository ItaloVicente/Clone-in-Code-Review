		invokeCompare(toolName);
		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testUserDefinedTool() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_CMD

