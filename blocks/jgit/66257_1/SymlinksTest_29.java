		try (Git git = new Git(db)) {
			writeTrashFile("c"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add symlink a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/b"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add folder a").call();
