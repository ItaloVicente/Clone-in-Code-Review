	private void commitGreeting() throws Exception {
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		git.commit().setMessage("a commit with a file").call();
	}

	@Test
	public void testDefaultFormatIsTar() throws Exception {
		commitGreeting();
				"git archive HEAD"
		assertArrayEquals(new String[] { "greeting" }
				listTarEntries(result));
	}

	private static String shellQuote(String s) {
		return "'" + s.replace("'"
	}

	@Test
	public void testFormatOverridesFilename() throws Exception {
		final File archive = new File(db.getWorkTree()
		final String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					"--format=zip " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertContainsEntryWithMode(path
		assertIsZip(archive);
	}

	@Test
	public void testUnrecognizedExtensionMeansTar() throws Exception {
		final File archive = new File(db.getWorkTree()
		final String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertTarContainsEntry(path
		assertIsTar(archive);
	}

	@Test
	public void testNoExtensionMeansTar() throws Exception {
		final File archive = new File(db.getWorkTree()
		final String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testExtensionMatchIsAnchored() throws Exception {
		final File archive = new File(db.getWorkTree()
		final String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testZipExtension() throws Exception {
		final File archiveWithDot = new File(db.getWorkTree()
		final File archiveNoDot = new File(db.getWorkTree()

		commitGreeting();
		execute("git archive " +
			shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
			"HEAD");
		execute("git archive " +
			shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
			"HEAD");
		assertIsZip(archiveWithDot);
		assertIsTar(archiveNoDot);
	}

	@Test
	public void testTarExtension() throws Exception {
		final File archive = new File(db.getWorkTree()
		final String path = archive.getAbsolutePath();

		commitGreeting();
		assertArrayEquals(new String[] { "" }
				execute("git archive " +
					shellQuote("--output=" + path) + " " +
					"HEAD"));
		assertIsTar(archive);
	}

	@Test
	public void testTgzExtensions() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.gz"
			final File archiveWithDot = new File(db.getWorkTree()
			final File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsGzip(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
	}

	@Test
	public void testTbz2Extension() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.bz2"
			final File archiveWithDot = new File(db.getWorkTree()
			final File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsBzip2(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
	}

	@Test
	public void testTxzExtension() throws Exception {
		commitGreeting();

		for (String ext : Arrays.asList("tar.xz"
			final File archiveWithDot = new File(db.getWorkTree()
			final File archiveNoDot = new File(db.getWorkTree()

			execute("git archive " +
				shellQuote("--output=" + archiveWithDot.getAbsolutePath()) + " " +
				"HEAD");
			execute("git archive " +
				shellQuote("--output=" + archiveNoDot.getAbsolutePath()) + " " +
				"HEAD");
			assertIsXz(archiveWithDot);
			assertIsTar(archiveNoDot);
		}
	}

