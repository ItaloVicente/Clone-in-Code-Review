		assertEquals("FormTextModel does not return the originally provided text", "<form>Foo &Bar</form>",
				formTextModel.getRawText());
	}

	@Test
	public void testXMLWhitespaceNormalized() throws Exception {
		FormTextModel formTextModel = new FormTextModel();
		formTextModel.setWhitespaceNormalized(true);
		formTextModel.parseInputStream(
				new ByteArrayInputStream("<form><p>   line with   \r\n   <b>  whitespace </b> Test </p></form>"
						.getBytes(StandardCharsets.UTF_8)),
				false);
		assertEquals("FormTextModel does not remove whitespace correctly according to the rules",
				"line with whitespace Test" + System.lineSeparator(), formTextModel.getAccessibleText());
		assertEquals("FormTextModel does not return the originally provided text",
				"<form><p>   line with   \r\n   <b>  whitespace </b> Test </p></form>", formTextModel.getRawText());
		formTextModel.parseInputStream(new ByteArrayInputStream(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><form><p>   line with å  \r\n   <b>  whitespace </b> Test </p></form>"
						.getBytes("ISO-8859-1")),
				false);
		assertEquals("FormTextModel does not remove whitespace correctly according to the rules",
				"line with å whitespace Test" + System.lineSeparator(), formTextModel.getAccessibleText());
		assertEquals("FormTextModel does not return the originally provided text",
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><form><p>   line with å  \r\n   <b>  whitespace </b> Test </p></form>",
				formTextModel.getRawText());
	}

	@Test
	public void testXMLWhitespaceNotNormalized() throws Exception {
		FormTextModel formTextModel = new FormTextModel();
		formTextModel.setWhitespaceNormalized(false);
		formTextModel.parseInputStream(
				new ByteArrayInputStream("<form><p>   line with      <b>  whitespace </b> Test </p></form>"
						.getBytes(StandardCharsets.UTF_8)),
				false);
		assertEquals("FormTextModel does not preserve whitespace correctly according to the rules",
				"   line with        whitespace  Test " + System.lineSeparator(), formTextModel.getAccessibleText());
		assertEquals("FormTextModel does not return the originally provided text",
				"<form><p>   line with      <b>  whitespace </b> Test </p></form>", formTextModel.getRawText());
		formTextModel.parseInputStream(new ByteArrayInputStream(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><form><p>   line with å     <b>  whitespace </b> Test </p></form>"
						.getBytes("ISO-8859-1")),
				false);
		assertEquals("FormTextModel does not preserve whitespace correctly according to the rules",
				"   line with å       whitespace  Test " + System.lineSeparator(), formTextModel.getAccessibleText());
		assertEquals("FormTextModel does not return the originally provided text",
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><form><p>   line with å     <b>  whitespace </b> Test </p></form>",
				formTextModel.getRawText());
