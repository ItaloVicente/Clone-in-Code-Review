		writeTrashFile(FILE1, FILE1);
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree(), FILE1).exists());

		writeTrashFile("file2", "file2");
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree(), "file2").exists());

		git.rebase().setUpstream("HEAD~1")
				.runInteractively(new InteractiveHandler() {
