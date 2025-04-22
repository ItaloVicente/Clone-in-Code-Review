	@Test
	public void insertChangeIdIgnoresExisting() throws Exception {
		String msg = "message\n"
				+ "\n"
				+	"Change-Id: Ideadbeefdeadbeefdeadbeefdeadbeefdeadbeef\n";
		RevCommit c = tr.commit().message(msg).insertChangeId().create();
		rw.parseBody(c);
		assertEquals(msg
	}

