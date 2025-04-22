	public void testShorteningNoteRefName() throws Exception {
		String expectedShortName = "review";
		String noteRefName = Constants.R_NOTES + expectedShortName;
		assertEquals(expectedShortName
		String nonNoteRefName = Constants.R_HEADS + expectedShortName;
		assertEquals(nonNoteRefName
	}

