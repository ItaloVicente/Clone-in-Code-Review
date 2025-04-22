	private String describe(ObjectId c1, String... patterns) throws GitAPIException, IOException, InvalidPatternException {
		return git.describe().setTarget(c1).setMatch(patterns).call();
	}

	private static void assertNameStartsWith(ObjectId c4, String prefix) {
		assertTrue(c4.name(), c4.name().startsWith(prefix));
