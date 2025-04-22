	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidScmUriWithoutPath() throws Exception {
		new GitURI(URI
				.create("scm:git:git://git.eclipse.org/gitroot/cdo/cdo.git"));
	}

