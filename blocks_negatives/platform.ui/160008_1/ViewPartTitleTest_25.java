	private void verifySettings(IWorkbenchPart2 part, String expectedTitle,
			String expectedPartName, String expectedContentDescription)
			throws Exception {
		Assert.assertEquals("Incorrect view title", expectedTitle, part
				.getTitle());
		Assert.assertEquals("Incorrect part name", expectedPartName, part
				.getPartName());
		Assert.assertEquals("Incorrect content description",
				expectedContentDescription, part.getContentDescription());

		Assert.assertEquals("Incorrect title in view reference", expectedTitle,
				ref.getTitle());
		Assert.assertEquals("Incorrect part name in view reference",
				expectedPartName, ref.getPartName());
		Assert.assertEquals("Incorrect content description in view reference",
				expectedContentDescription, ref.getContentDescription());
