	@Test
	public void archiveHeadAllFilesTarTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tar";
			File archive = new File(getTemporaryDirectory()
					"archive." + format);
			archive(git
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					ArchiveInputStream o = new TarArchiveInputStream(bi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git
			assertEquals(UNEXPECTED_DIFFERENT_HASH
					ObjectId.fromRaw(IO.readFully(archive)));
		}
	}

	@Test
	public void archiveHeadAllFilesTgzTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tgz";
			File archive = new File(getTemporaryDirectory()
					"archive." + fmt);
			archive(git
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					InputStream gzi = new GzipCompressorInputStream(bi);
					ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git
			assertEquals(UNEXPECTED_DIFFERENT_HASH
					ObjectId.fromRaw(IO.readFully(archive)));
		}
	}

	@Test
	public void archiveHeadAllFilesTbz2Timestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tbz2";
			File archive = new File(getTemporaryDirectory()
					"archive." + fmt);
			archive(git
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					InputStream gzi = new BZip2CompressorInputStream(bi);
					ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git
			assertEquals(UNEXPECTED_DIFFERENT_HASH
					ObjectId.fromRaw(IO.readFully(archive)));
		}
	}

	@Test
	public void archiveHeadAllFilesTxzTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "txz";
			File archive = new File(getTemporaryDirectory()
			archive(git
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					InputStream gzi = new XZCompressorInputStream(bi);
					ArchiveInputStream o = new TarArchiveInputStream(gzi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git
			assertEquals(UNEXPECTED_DIFFERENT_HASH
					ObjectId.fromRaw(IO.readFully(archive)));
		}
	}

	@Test
	public void archiveHeadAllFilesZipTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "zip";
			File archive = new File(getTemporaryDirectory()
			archive(git
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					ArchiveInputStream o = new ZipArchiveInputStream(bi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git
			assertEquals(UNEXPECTED_DIFFERENT_HASH
					ObjectId.fromRaw(IO.readFully(archive)));
		}
	}

	private void createTestContent(Git git) throws IOException
			NoFilepatternException
			UnmergedPathsException
			WrongRepositoryStateException
		writeTrashFile("file_1.txt"
		git.add().addFilepattern("file_1.txt").call();
		git.commit().setMessage("create file").call();

		writeTrashFile("file_1.txt"
		writeTrashFile("file_2.txt"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("updated file").call();
	}

	private static void archive(Git git
			throws GitAPIException
			FileNotFoundException
			IncorrectObjectTypeException
		git.archive().setOutputStream(new FileOutputStream(archive))
				.setFormat(fmt)
				.setTree(git.getRepository().resolve("HEAD")).call();
	}

	private static void assertEntries(ArchiveInputStream o) throws IOException {
		ArchiveEntry e;
		int n = 0;
		while ((e = o.getNextEntry()) != null) {
			n++;
			assertEquals(UNEXPECTED_LAST_MODIFIED
					(1250379778668L / 1000L) * 1000L
					e.getLastModifiedDate().getTime());
		}
		assertEquals(UNEXPECTED_ARCHIVE_SIZE
	}

