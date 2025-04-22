	@Test
	void testUndefinedTool() throws Exception {
		assertThrows(ToolException.class
			String toolName = "undefined";
			invokeCompare(toolName);
			fail("Expected exception to be thrown due to not defined external diff tool");
		});
