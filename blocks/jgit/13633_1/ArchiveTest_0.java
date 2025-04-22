	@Test
	public void testArchiveWithLongFilename() throws Exception {
		String filename = "1234567890";
		for (int i = 0; i < 20; i++)
			filename = filename + "/1234567890";
		writeTrashFile(filename
		git.add().addFilepattern("1234567890").call();
		git.commit().setMessage("file with long name").call();

				"git archive HEAD"
		assertArrayEquals(new String[] { filename }
				listZipEntries(result));
	}

	@Test
	public void testTarWithLongFilename() throws Exception {
		String filename = "1234567890";
		for (int i = 0; i < 20; i++)
			filename = filename + "/1234567890";
		writeTrashFile(filename
		git.add().addFilepattern("1234567890").call();
		git.commit().setMessage("file with long name").call();

				"git archive --format=tar HEAD"
		assertArrayEquals(new String[] { filename }
				listTarEntries(result));
	}

