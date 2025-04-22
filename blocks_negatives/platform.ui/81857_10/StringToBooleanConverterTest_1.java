	@Override
	protected void setUp() throws Exception {
		super.setUp();

		converter = new StringToBooleanConverter();
		assertTrue(trueValues.size() > 0);
		assertTrue(falseValues.size() > 0);
	}

