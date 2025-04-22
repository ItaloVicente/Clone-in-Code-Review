	@Test
	public void test() {
		final HTTPSupport httpSupport = new HTTPSupport() {
			@Override
			protected JGitFileSystemProvider resolveProvider() {
				return provider;
			}
		};
