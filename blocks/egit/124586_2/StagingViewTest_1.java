	private StagingViewTester commitOneFileChange(String fileContent)
			throws Exception {
		return commitOneFileChange(fileContent, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER);
	}

	private StagingViewTester commitOneFileChange(String fileContent,
			String author,
			String committer) throws Exception {
