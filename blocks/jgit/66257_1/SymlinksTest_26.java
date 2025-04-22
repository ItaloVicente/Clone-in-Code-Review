		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add files a & b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add symlink a").call();
