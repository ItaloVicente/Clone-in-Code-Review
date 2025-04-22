	@Test
	public void shouldLogRefsHeads() throws Exception {
		String headRef = Constants.R_HEADS + "foo";
		String logMessage = "foo message";
		ReflogWriter writer = new ReflogWriter(
				(RefDirectory) db.getRefDatabase());

		writer.log(headRef
		assertTrue(new File(db.getDirectory()

		String expectedLog = oneLinePrefix + logMessage;
		byte[] buffer = new byte[expectedLog.getBytes(UTF_8).length];
		readReflog(buffer
		assertEquals(expectedLog
	}

	@Test
	public void shouldNotLogRemoteRefs() throws Exception {
		String remoteRef = Constants.R_REMOTES + "foo";
		ReflogWriter writer = new ReflogWriter(
				(RefDirectory) db.getRefDatabase());

		writer.log(remoteRef
		assertFalse(new File(db.getDirectory()
	}

	private void readReflog(byte[] buffer
