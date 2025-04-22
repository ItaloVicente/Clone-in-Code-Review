	@Theory
	public void mergeConflictWithCrLfTextAuto(MergeStrategy strategy)
			throws IOException
		Git git = Git.wrap(db);
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("base").call();
		assertEquals("[crlf.txt
				indexState(CONTENT));
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("attributes").call();

		git.branchCreate().setName("brancha").call();

		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on master").call();
		assertEquals(
				"[.gitattributes
						+ "[crlf.txt
				indexState(CONTENT));

		git.checkout().setName("brancha").call();
		File testFile = writeTrashFile("crlf.txt"
				"a crlf file\r\nanother line\r\n");
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		MergeResult mergeResult = git.merge().setStrategy(strategy)
				.include(db.resolve("master")).call();
		assertEquals(MergeResult.MergeStatus.CONFLICTING
				mergeResult.getMergeStatus());
		checkFile(testFile
						+ ">>>>>>> 8e9e704742f1bc8a41eac88aac4aeefd338b7384\n");
	}

