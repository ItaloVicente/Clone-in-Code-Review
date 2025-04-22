	private String[] executeExpectingException(String command) {
		try {
			execute(command);
			throw new AssertionError("Expected Die");
		} catch (Exception e) {
			return e.getMessage().split(System.lineSeparator());
		}
	}
