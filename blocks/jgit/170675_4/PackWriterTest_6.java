	@Parameterized.Parameter(0)
	public boolean useMmap;

	@Parameterized.Parameters(name = "useMmap={0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{Boolean.FALSE}
				{Boolean.TRUE}});
	}
