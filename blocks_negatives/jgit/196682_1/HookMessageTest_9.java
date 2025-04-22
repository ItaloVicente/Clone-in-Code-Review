	@Test
	public void testPush_HookMessagesToOutputStream() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q", Q_txt).create();
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
