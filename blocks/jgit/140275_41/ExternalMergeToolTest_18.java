		invokeMerge(toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testKdiff3() throws Exception {
		assumePosixPlatform();

		CommandLineMergeTool autoMergingTool = CommandLineMergeTool.kdiff3;
		assumeMergeToolIsAvailable(autoMergingTool);

		CommandLineMergeTool tool = autoMergingTool;
		PreDefinedMergeTool externalTool = new PreDefinedMergeTool(tool.name()
				tool.getPath()
				tool.getParameters(false)
				tool.isExitCodeTrustable() ? BooleanTriState.TRUE
						: BooleanTriState.FALSE);

