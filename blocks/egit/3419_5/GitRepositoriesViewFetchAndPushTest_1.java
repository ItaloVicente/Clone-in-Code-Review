		resultDialog = waitForFetchResultDialog();
		assertEquals("Wrong result table row count", 0, resultDialog.bot().table()
				.rowCount());
	}

	private void fetchFromOrigin(SWTBotTree tree) throws Exception,
			InterruptedException {
