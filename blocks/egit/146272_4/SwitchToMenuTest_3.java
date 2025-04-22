		assertTrue(new File(gitDir, Constants.LOGS + "/" + Constants.HEAD)
				.delete());
	}

	@Test
	public void selectionWithCorruptedReflog() throws Exception {
		File gitDir = createProjectAndCommitToRepository();

		try (Git git = new Git(lookupRepository(gitDir))) {
			git.checkout().setName("stable").call();
			git.checkout().setName("master").call();
		}

		File reflog = new File(gitDir, Constants.LOGS + "/" + Constants.HEAD);
		List<String> lines = Files.readAllLines(reflog.toPath());
		assertTrue("Expected some lines in the reflog", lines.size() > 1);
		lines.add(1,
				"INTENTIONALLY CORRUPTED REFLOG. Just some text that definitely shouldn't be in a reflog.");
		Files.write(reflog.toPath(), lines);

		selectionWithProj1Common();

		assertTrue(reflog.delete());
