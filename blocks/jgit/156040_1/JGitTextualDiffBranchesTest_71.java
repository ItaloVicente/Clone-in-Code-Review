	@Test(expected = GitException.class)
	public void testDiffWithNonExistentBranch() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isEmpty();
	}
