	@Parameters(name = "git tag -a {0}?-a: with git describe {1}?--tags:")
	public static Collection<Boolean[]> getUseAnnotatedTagsValues() {
		return Arrays.asList(new Boolean[][] { { Boolean.TRUE, Boolean.FALSE },
				{ Boolean.FALSE, Boolean.FALSE },
				{ Boolean.TRUE, Boolean.TRUE },
				{ Boolean.FALSE, Boolean.TRUE } });
