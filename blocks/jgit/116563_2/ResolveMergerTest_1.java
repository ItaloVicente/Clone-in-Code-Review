	@Theory
	public void mergeWithCrlfAutoCrlfTrue(MergeStrategy strategy)
			throws IOException
		Git git = Git.wrap(db);
		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("base").call();

		git.branchCreate().setName("brancha").call();

		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on master").call();

		git.checkout().setName("brancha").call();
		File testFile = writeTrashFile("crlf.txt"
				"a first line\r\na crlf file\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		MergeResult mergeResult = git.merge().setStrategy(strategy)
				.include(db.resolve("master")).call();
		assertEquals(MergeResult.MergeStatus.MERGED
				mergeResult.getMergeStatus());
		checkFile(testFile
	}

