	@Test
	public void testParse_GitStyleMessageWithCRLF() throws Exception {
		final String shortMsg = "This fixes a bug.";
		final String body = "We do it with magic and pixie dust and stuff.\r\n"
				+ "\r\n" + "Signed-off-by: A U. Thor <author@example.com>\r\n";
		final String fullMsg = shortMsg + "\r\n" + "\r\n" + body;
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

