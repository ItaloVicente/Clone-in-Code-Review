		try (Repository clonedRepo = FileRepositoryBuilder.create(new File(workdir2,
				Constants.DOT_GIT))) {
			assertTrue(
					clonedRepo.getConfig()
					.getStringList(ConfigConstants.CONFIG_REMOTE_SECTION,
							"origin", "fetch")[1].equals("refs/notes/review:refs/notes/review"));
			try (Git clonedGit = new Git(clonedRepo)) {
				assertEquals(1, clonedGit.notesList()
						.setNotesRef("refs/notes/review").call().size());
			}
		}
