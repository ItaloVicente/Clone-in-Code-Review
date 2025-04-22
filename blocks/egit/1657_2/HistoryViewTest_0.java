		assertEquals("Wrong number of commits", 1,
				getHistoryViewTable(PROJ1, FOLDER, FILE2).rowCount());
		assertEquals("Wrong number of commits", 1,
				getHistoryViewTable(PROJ1, SECONDFOLDER).rowCount());
		assertEquals("Wrong number of commits", 1,
				getHistoryViewTable(PROJ1, SECONDFOLDER, ADDEDFILE).rowCount());
