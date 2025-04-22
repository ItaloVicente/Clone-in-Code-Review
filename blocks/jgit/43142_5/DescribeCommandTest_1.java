	@Parameter
	public boolean useAnnotatedTags;

	@Parameters
	public static Collection<Boolean[]> getUseAnnotatedTagsValues() {
		return Arrays.asList(new Boolean[][] { { Boolean.TRUE }
				{ Boolean.FALSE } });
	}

