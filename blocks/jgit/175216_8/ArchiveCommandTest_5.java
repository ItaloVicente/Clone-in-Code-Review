	@Ignore
	public void archiveHeadAllFilesTxzWithCompressionReducesArchiveSize() throws Exception {
		archiveHeadAllFilesWithCompression("txz");
	}

	@Test
	public void archiveHeadAllFilesZipWithCompressionReducesArchiveSize() throws Exception {
		archiveHeadAllFilesWithCompression("zip");
	}

	private void archiveHeadAllFiles(String fmt) throws Exception {
