	@Test
	public void testDiffWithUpdateFirstAndLastLines() throws IOException {
		commit(git
				multiline(TXT_FILES.get(1)

		List<TextualDiff> fileDiffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
	}

	@Test
	public void testDiffWithEvenBranches() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH
