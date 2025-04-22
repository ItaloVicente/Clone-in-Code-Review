	@Test
	void testUndefinedTool() throws Exception {
		assertThrows(Die.class
			String toolName = "undefined";
			String[] conflictingFilenames = createUnstagedChanges();

			List<String> expectedErrors = new ArrayList<>();
			for (String changedFilename : conflictingFilenames) {
				expectedErrors
						.add("External diff tool is not defined: " + toolName);
				expectedErrors.add("compare of " + changedFilename + " failed");
			}
