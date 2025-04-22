	private IEclipsePreferences prefs;

	private boolean prefEnabled;

	@Parameter
	public Boolean linkingDisabled;

	@Parameters(name = "linkingDisabled={0}")
	public static List<Boolean> getParameters() {
		return Arrays.asList(Boolean.FALSE, Boolean.TRUE);
	}

