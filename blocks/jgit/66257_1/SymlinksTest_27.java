		try (Git git = new Git(db)) {
			writeTrashFile("b"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & symlink a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();
