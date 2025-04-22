		Git git = new Git(db);
		writeTrashFile("b", "Hello world b");
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add file b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		FileUtils.createSymLink(new File(db.getWorkTree(), "a"), "b");
		git.add().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("add symlink a").call();
