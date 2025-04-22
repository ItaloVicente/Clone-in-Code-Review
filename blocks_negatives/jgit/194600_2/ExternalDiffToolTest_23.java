	public void testNotAvailableTools() {
		DiffTools manager = new DiffTools(db);
		Set<String> actualToolNames = manager.getNotAvailableTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of not available external diff tools",
				expectedToolNames, actualToolNames);
	}
