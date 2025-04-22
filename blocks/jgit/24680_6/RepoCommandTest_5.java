
		notDefaultDb = createWorkRepository();
		git = new Git(notDefaultDb);
		JGitTestUtil.writeTrashFile(notDefaultDb
		git.add().addFilepattern("world.txt").call();
		git.commit().setMessage("Initial commit").call();

		groupADb = createWorkRepository();
		git = new Git(groupADb);
		JGitTestUtil.writeTrashFile(groupADb
		git.add().addFilepattern("a.txt").call();
		git.commit().setMessage("Initial commit").call();

		groupBDb = createWorkRepository();
		git = new Git(groupBDb);
		JGitTestUtil.writeTrashFile(groupBDb
		git.add().addFilepattern("b.txt").call();
		git.commit().setMessage("Initial commit").call();

		resolveRelativeUris();
