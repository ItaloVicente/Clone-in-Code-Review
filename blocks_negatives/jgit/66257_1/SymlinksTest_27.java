		Git git = new Git(db);
		writeTrashFile("b", "Hello world b");
		FileUtils.createSymLink(new File(db.getWorkTree(), "a"), "b");
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit().setMessage("add file b & symlink a")
				.call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		RevCommit commit2 = git.commit().setMessage("delete symlink a").call();
