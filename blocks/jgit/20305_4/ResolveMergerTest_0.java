	@Theory
	public void mergeWithCrlfInWT(MergeStrategy strategy) throws IOException
			GitAPIException {
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
		writeTrashFile("crlf.txt"
		git.add().addFilepattern("crlf.txt").call();
		git.commit().setMessage("on brancha").call();

		db.getConfig().setString("core"
		db.getConfig().save();
		writeTrashFile("crlf.txt"

		MergeResult mergeResult = git.merge().setStrategy(strategy)
				.include(db.resolve("master"))
				.call();
		assertEquals(MergeResult.MergeStatus.MERGED
				mergeResult.getMergeStatus());
	}

