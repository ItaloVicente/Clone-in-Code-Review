
	@Test
	public void testTextWithAmpersand() {
		FormTextModel formTextModel = new FormTextModel();
		formTextModel.parseTaggedText("<form>Foo &Bar</form>", false);
		assertEquals("Foo &Bar" + System.lineSeparator(),
				formTextModel.getAccessibleText());
	}

