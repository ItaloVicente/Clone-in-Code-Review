
	@Test
	public void testPush_HookMessagesToOutputStream() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		Transport t;
		PushResult result;

		t = Transport.open(db
		OutputStream out = new ByteArrayOutputStream();
		try {
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate update = new RemoteRefUpdate(src.getRepository()
					srcExpr
			result = t.push(NullProgressMonitor.INSTANCE
					Collections.singleton(update)
		} finally {
			t.close();
		}

				+ "come back next year!\n";
		assertEquals(expectedMessage
				result.getMessages());

		assertEquals(expectedMessage
	}

