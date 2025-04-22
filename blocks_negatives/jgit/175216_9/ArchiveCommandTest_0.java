		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tar";
			File archive = new File(getTemporaryDirectory(),
					"archive." + format);
			archive(git, archive, fmt);
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));

			try (InputStream fi = Files.newInputStream(archive.toPath());
					InputStream bi = new BufferedInputStream(fi);
					ArchiveInputStream o = new TarArchiveInputStream(bi)) {
				assertEntries(o);
			}

			Thread.sleep(WAIT);
			archive(git, archive, fmt);
			assertEquals(UNEXPECTED_DIFFERENT_HASH, hash1,
					ObjectId.fromRaw(IO.readFully(archive)));
		}
