	@Test
	public void testDiscardChangesWithPath() throws Exception {
		IFile file1 = project.getFile(new Path("folder1/file1.txt"));
		setNewFileContent(file1, "changed 1");

		DiscardChangesOperation operation = new DiscardChangesOperation(
				Arrays.asList(file1.getLocation()));
		operation.execute(new NullProgressMonitor());

		assertEquals("Hello world 1",
				testUtils.slurpAndClose(file1.getContents()));
	}

	@Test
	public void testDiscardChangesWithStage() throws Exception {
		Git git = Git.wrap(repository1.getRepository());
		File file = new File(repository1.getRepository().getWorkTree(),
				"conflict.txt");
		repository1.appendFileContent(file, "base", false);
		git.add().addFilepattern("conflict.txt").call();
		git.commit().setMessage("commit").call();

		git.checkout().setCreateBranch(true).setName("side").call();
		repository1.appendFileContent(file, "side", false);
		git.add().addFilepattern("conflict.txt").call();
		RevCommit side = git.commit().setMessage("commit on side").call();

		git.checkout().setName("master").call();
		repository1.appendFileContent(file, "master", false);
		git.add().addFilepattern("conflict.txt").call();
		git.commit().setMessage("commit on master").call();

		git.merge().include(side).call();

		DirCache dirCache = repository1.getRepository().readDirCache();
		assertEquals(1, dirCache.getEntry("conflict.txt").getStage());

		IPath path = new Path(file.getAbsolutePath());
		DiscardChangesOperation operation = new DiscardChangesOperation(
				Arrays.asList(path));
		operation.setStage(Stage.THEIRS);
		operation.execute(new NullProgressMonitor());

		DirCache dirCacheAfter = repository1.getRepository().readDirCache();
		assertEquals("Expected index to be unmodified", 1, dirCacheAfter
				.getEntry("conflict.txt").getStage());

		assertEquals("side", new String(IO.readFully(file), "UTF-8"));
	}

