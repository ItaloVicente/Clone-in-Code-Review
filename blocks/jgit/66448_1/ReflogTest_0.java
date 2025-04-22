		try (Git git = new Git(db)) {
			git.commit().setMessage("first commit").call();
			git.checkout().setCreateBranch(true).setName("side").call();
			writeTrashFile("file"
			git.add().addFilepattern("file").call();
			git.commit().setMessage("side commit").call();
