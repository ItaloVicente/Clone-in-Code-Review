	private void verifySettings(IWorkbenchPart2 part, String expectedTitle, String expectedPartName,
			String expectedContentDescription) throws Exception {
		assertEquals("Incorrect view title", expectedTitle, part.getTitle());
		assertEquals("Incorrect part name", expectedPartName, part.getPartName());
		assertEquals("Incorrect content description", expectedContentDescription, part.getContentDescription());

		assertEquals("Incorrect title in view reference", expectedTitle, ref.getTitle());
		assertEquals("Incorrect part name in view reference", expectedPartName, ref.getPartName());
		assertEquals("Incorrect content description in view reference", expectedContentDescription,
				ref.getContentDescription());
