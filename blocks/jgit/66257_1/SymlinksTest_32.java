		try (Git git = new Git(db);
				TreeWalk tw = new TreeWalk(db);) {
			writeTrashFile("b"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("add file b & symlink a")
					.call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			RevCommit commit2 = git.commit().setMessage("delete symlink a").call();
