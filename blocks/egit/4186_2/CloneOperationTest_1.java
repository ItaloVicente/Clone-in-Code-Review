	@Test
	public void testConfigureFetchAfterCloneTask() throws Exception {
		createNoteInOrigin();

		URIish uri = new URIish("file:///"
				+ repository1.getRepository().getDirectory().toString());
		CloneOperation clop = new CloneOperation(uri, true, null, workdir2,
				"refs/heads/master", "origin", 0);

		clop.addPostCloneTask(new ConfigureFetchAfterCloneTask("origin",
				"refs/notes/review:refs/notes/review"));
		clop.run(null);
		Repository clonedRepo = new FileRepository(new File(workdir2,
				Constants.DOT_GIT));
		assertTrue(
				clonedRepo.getConfig()
				.getStringList(ConfigConstants.CONFIG_REMOTE_SECTION,
						"origin", "fetch")[1].equals("refs/notes/review:refs/notes/review"));
		Git clonedGit = new Git(clonedRepo);
		assertEquals(1, clonedGit.notesList().setNotesRef("refs/notes/review").call().size());
	}

	protected void createNoteInOrigin() throws NoFilepatternException, NoHeadException,
			NoMessageException, UnmergedPathException,
			ConcurrentRefUpdateException, WrongRepositoryStateException {
		Git git = new Git(repository1.getRepository());
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("Initial commit").call();
		git.notesAdd().setNotesRef("refs/notes/review").setObjectId(commit).setMessage("text").call();
	}

