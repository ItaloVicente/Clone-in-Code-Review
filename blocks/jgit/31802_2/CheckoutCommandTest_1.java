	private void assertHeadDetached() throws IOException {
		Ref head = db.getRef(Constants.HEAD);
		assertFalse(head.isSymbolic());
		assertSame(head
	}

