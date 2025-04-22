	@Test
	public void insertExplicitChangeId() throws Exception {
		RevCommit c = tr.commit().message("message")
				.insertChangeId("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef")
				.create();
		rw.parseBody(c);
		assertEquals("message\n\n"
				+ "Change-Id: Ideadbeefdeadbeefdeadbeefdeadbeefdeadbeef\n"
				
	}

