		defaultGit.add().addFilepattern("hello.txt").call();
		defaultGit.commit().setMessage("Initial commit").call();

		remoteDb = createWorkRepository();
		notDefaultGit = new Git(remoteDb);
		JGitTestUtil.writeTrashFile(remoteDb
		notDefaultGit.add().addFilepattern("world.txt").call();
		notDefaultGit.commit().setMessage("Initial commit").call();

		remoteDb = createWorkRepository();
		groupAGit = new Git(remoteDb);
		JGitTestUtil.writeTrashFile(remoteDb
		groupAGit.add().addFilepattern("a.txt").call();
		groupAGit.commit().setMessage("Initial commit").call();

		remoteDb = createWorkRepository();
		groupBGit = new Git(remoteDb);
		JGitTestUtil.writeTrashFile(remoteDb
		groupBGit.add().addFilepattern("b.txt").call();
		groupBGit.commit().setMessage("Initial commit").call();

		resolveRelativeUris();
