	@Parameters
	public static Object[] data() {
		return new Object[] { true, false };
	}

	/** If true, create simple app setup, otherwise a traditional setup */
	@Parameter
	public boolean simple;

