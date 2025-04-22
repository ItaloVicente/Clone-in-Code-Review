	@Parameters(name = "OldRule? {0}")
	public static Iterable<Boolean[]> data() {
		return Arrays.asList(new Boolean[][] { { Boolean.FALSE },
				{ Boolean.TRUE } });
	}

	@Parameter
	public Boolean useOldRule;

