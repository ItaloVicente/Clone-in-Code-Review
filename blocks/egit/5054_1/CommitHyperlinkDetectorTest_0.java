	@Test
	public void testMultiLine() {
		setText("Test multi-line text\n" + EXAMPLE_ID);
		IHyperlink[] hyperlinks = detectHyperlinks(0,textViewer.getDocument().getLength());
		assertEquals(1, hyperlinks.length);
		assertEquals(EXAMPLE_ID, hyperlinks[0].getHyperlinkText());
		assertEquals(new Region(21, EXAMPLE_ID.length()), hyperlinks[0].getHyperlinkRegion());
	}

	@Test
	public void testGerritId() {
		setText("I" + EXAMPLE_ID);
		IHyperlink[] hyperlinks = detectHyperlinks(0,textViewer.getDocument().getLength());
		assertNull(hyperlinks);
	}

	@Test
	public void testGerritIdWithinText() {
		setText("abc I" + EXAMPLE_ID);
		IHyperlink[] hyperlinks = detectHyperlinks(5,textViewer.getDocument().getLength());
		assertNull(hyperlinks);
	}


