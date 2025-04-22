		configureEchoTool(TOOL_NAME);
	}

	@Test(expected = Die.class)
	public void testNotDefinedTool() throws Exception {
		createUnstagedChanges();

		runAndCaptureUsingInitRaw("difftool"
		fail("Expected exception when trying to run undefined tool");
