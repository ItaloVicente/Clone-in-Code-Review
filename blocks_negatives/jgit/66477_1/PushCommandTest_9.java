		RefSpec spec = new RefSpec("refs/heads/*:refs/heads/*");
		git1.push().setRemote("test").setRefSpecs(spec).call();

		git2.branchCreate().setName("refs/heads/other").call();
		git2.checkout().setName("refs/heads/other").call();

		writeTrashFile("a", "content of a");
		git2.add().addFilepattern("a").call();
		RevCommit commit2 = git2.commit().setMessage("adding a").call();

		Properties res = git1.gc().setExpire(null).call();
		assertEquals(7, res.size());

		writeTrashFile("b", "content of b");
		git1.add().addFilepattern("b").call();
		RevCommit commit3 = git1.commit().setMessage("adding b").call();

		try {
