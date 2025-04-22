		String origLineSeparator = System.getProperty("line.separator", "\n");
		System.setProperty("line.separator", "\n");
		try {
			setupGitAndDoHardReset(null, EOL.NATIVE, "*.txt text", null, null);
			collectRepositoryState();
			assertEquals("text", entryCRLF.attrs);
			checkEntryContent(entryCRLF, CONTENT_LF, CONTENT_LF);
			checkEntryContent(entryLF, CONTENT_LF, CONTENT_LF);
		} finally {
			System.setProperty("line.separator", origLineSeparator);
		}
