	@Test
	public void archiveHeadAllFilesTgzTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file_1.txt"
			git.add().addFilepattern("file_1.txt").call();
			git.commit().setMessage("create file").call();

			writeTrashFile("file_1.txt"
			writeTrashFile("file_2.txt"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("updated file").call();

			File archive = new File(getTemporaryDirectory()
			git.archive().setOutputStream(new FileOutputStream(archive))
					.setFormat("tgz")
					.setTree(git.getRepository().resolve("HEAD")).call();

			try (InputStream fi = Files.newInputStream(archive.toPath());
				     InputStream bi = new BufferedInputStream(fi);
				     InputStream gzi = new GzipCompressorInputStream(bi);
				     ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				ArchiveEntry e;
				int n = 0;
				while ((e = o.getNextEntry()) != null) {
					n++;
					assertEquals(
							"expected lastModified mocked by MockSystemReader "
									+ "truncated to 1 second"
							(1250379778668L / 1000L) * 1000L
							e.getLastModifiedDate().getTime());
				}
				assertEquals(UNEXPECTED_ARCHIVE_SIZE
			}
		}
	}

	private static class MockFormat
			implements ArchiveCommand.Format<MockOutputStream> {
