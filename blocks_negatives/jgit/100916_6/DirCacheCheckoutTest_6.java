		Git git = Git.wrap(db);

		File file = writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("create file").call();
		assertWorkDir(mkmap(fname, "a"));

		git.branchCreate().setName("side").call();
