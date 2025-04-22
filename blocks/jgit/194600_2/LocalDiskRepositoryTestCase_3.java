	@Rule
	public TestName currentTest = new TestName();

	private String getTestName() {
		String name = currentTest.getMethodName();
		name = name.replaceAll("[^a-zA-Z0-9]"
		name = name.replaceAll("__+"
		if (name.startsWith("_")) {
			name = name.substring(1);
		}
		return name;
	}

