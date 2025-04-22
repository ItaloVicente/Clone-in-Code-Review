				"git archive --format=zip HEAD", db);
		assertArrayEquals(new String[] { payload }, //
				zipEntryContent(result, "xyzzy"));
	}

	@Test
	public void testTarPreservesContent() throws Exception {
		final String payload = "“The quick brown fox jumps over the lazy dog!”";
		writeTrashFile("xyzzy", payload);
		git.add().addFilepattern("xyzzy").call();
		git.commit().setMessage("add file with content").call();

				"git archive --format=tar HEAD", db);
		assertArrayEquals(new String[] { payload }, //
				tarEntryContent(result, "xyzzy"));
