		Git git = Git.wrap(db);

		writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("create file").call();

		git.branchCreate().setName("side").call();

		writeTrashFile(fname, "b");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("modify file").call();

		git.checkout().setName("side").call();
		git.rm().addFilepattern(fname).call();
		writeTrashFile(".gitignore", fname);
		git.add().addFilepattern(".gitignore").call();
		git.commit().setMessage("delete and ignore file").call();

		writeTrashFile(fname, "Something different");
		git.checkout().setName("master").call();
		assertWorkDir(mkmap(fname, "b"));
		assertTrue(git.status().call().isClean());
