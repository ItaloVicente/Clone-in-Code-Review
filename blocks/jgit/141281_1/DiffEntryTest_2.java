
	@Test
	public void shouldReportSubmoduleReplacedByFileMove() throws Exception {
		FileRepository submoduleStandalone = createWorkRepository();
		JGitTestUtil.writeTrashFile(submoduleStandalone
				"submodule");
		Git submoduleStandaloneGit = Git.wrap(submoduleStandalone);
		submoduleStandaloneGit.add().addFilepattern("fileInSubmodule").call();
		submoduleStandaloneGit.commit().setMessage("add file to submodule")
				.call();

		Repository submodule_db = Git.wrap(db).submoduleAdd()
				.setPath("modules/submodule")
				.setURI(submoduleStandalone.getDirectory().toURI().toString())
				.call();
		File submodule_trash = submodule_db.getWorkTree();
		addRepoToClose(submodule_db);
		writeTrashFile("fileInRoot"
		Git rootGit = Git.wrap(db);
		rootGit.add().addFilepattern("fileInRoot").call();
		rootGit.commit().setMessage("add submodule and root file").call();
		writeTrashFile("fileInRoot"
		rootGit.add().addFilepattern("fileInRoot").call();
		RevCommit firstCommit = rootGit.commit().setMessage("change root file")
				.call();
		rootGit.rm().setCached(true).addFilepattern("modules/submodule").call();
		recursiveDelete(submodule_trash);
		JGitTestUtil.deleteTrashFile(db
		writeTrashFile("modules/submodule/fileInRoot"
		rootGit.rm().addFilepattern("fileInRoot").addFilepattern("modules/")
				.call();
		rootGit.add().addFilepattern("modules/").call();
		RevCommit secondCommit = rootGit.commit()
				.setMessage("remove submodule and move root file")
				.call();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(firstCommit.getTree());
			walk.addTree(secondCommit.getTree());
			walk.setRecursive(true);
			List<DiffEntry> diffs = DiffEntry.scan(walk);
			assertEquals(3
			DiffEntry e = diffs.get(0);
			assertEquals(DiffEntry.ChangeType.DELETE
			assertEquals("fileInRoot"
			e = diffs.get(1);
			assertEquals(DiffEntry.ChangeType.DELETE
			assertEquals("modules/submodule"
			assertEquals(FileMode.GITLINK
			e = diffs.get(2);
			assertEquals(DiffEntry.ChangeType.ADD
			assertEquals("modules/submodule/fileInRoot"
		}

	}
