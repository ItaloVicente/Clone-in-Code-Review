	@Theory
	public void rebaseWithCrlfAutoCrlfTrue(MergeStrategy strategy)
			throws IOException
		Git git = Git.wrap(db);
		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		RevCommit first = git.commit().setMessage("base").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("brancha").call();

		File testFile = writeTrashFile("crlf.txt"
				"line 1\r\nmodified line\r\nline 3\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		git.checkout().setName("master").call();
		File otherFile = writeTrashFile("otherfile.txt"
		git.add().addFilepattern("otherfile.txt").call();
		git.commit().setMessage("on master").call();

		git.checkout().setName("brancha").call();
		checkFile(testFile
		assertFalse(otherFile.exists());

		RebaseResult rebaseResult = git.rebase().setStrategy(strategy)
				.setUpstream(db.resolve("master")).call();
		assertEquals(RebaseResult.Status.OK
		checkFile(testFile
		checkFile(otherFile
		assertEquals(
				"[crlf.txt
						+ "[otherfile.txt
				indexState(CONTENT));
	}

