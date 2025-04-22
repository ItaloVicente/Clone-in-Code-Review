		File theFile = writeTrashFile("file1", "1\n2\n3\n");
		git.add().addFilepattern("file1").call();
		RevCommit firstInMaster = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree(), "file1").exists());
		writeTrashFile("file1", "1master\n2\n3\n");
		checkFile(theFile, "1master\n2\n3\n");
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("change file1 in master").call();

