	@Test
	public void testNotAvailableTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getNotAvailableTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of not available external merge tools",
				expectedToolNames, actualToolNames);
	}

