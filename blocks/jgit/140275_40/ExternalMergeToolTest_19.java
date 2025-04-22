		ExecutionResult result = manager.merge(local
				null
		assertEquals("Expected merge tool to succeed"

		List<String> actualLines = Files.readAllLines(mergedFile.toPath());
		String actualMergeResult = String.join(System.lineSeparator()
				actualLines);
		String expectedMergeResult = DEFAULT_CONTENT;
		assertEquals(
				"Failed to merge equal local and remote versions with pre-defined tool: "
						+ tool.getPath()
				expectedMergeResult
	}
