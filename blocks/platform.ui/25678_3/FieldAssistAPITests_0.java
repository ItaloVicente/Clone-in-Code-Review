		assertEquals("3.3", content.length(), proposal.getCursorPosition());
	}

	public void testContentProposalWithDescription() {
		proposal = new ContentProposal(content, description);
		assertEquals("2.0", content, proposal.getContent());
		assertEquals("2.1", content, proposal.getLabel());
		assertEquals("2.2", description, proposal.getDescription());
		assertEquals("2.3", content.length(), proposal.getCursorPosition());
	}

	public void testInitializationWithInvalidCursor() {
