	@Test
	void testUndefinedTool() throws Exception {
		assertThrows(ToolException.class
			String toolName = "undefined";
			invokeMerge(toolName);
			fail("Expected exception to be thrown due to not defined external merge tool");
		});
