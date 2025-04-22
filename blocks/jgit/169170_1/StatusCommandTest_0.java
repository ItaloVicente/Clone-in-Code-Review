
	@Test
	public void testFolderPrefix() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("audi"
			writeTrashFile("audio-new"
			writeTrashFile("audio.new"
			writeTrashFile("audio"
			writeTrashFile("audio_new"
			Status stat = git.status().call();
			assertEquals(Sets.of("audi"
					"audio_new")
		}
	}

