		try (Git git = new Git(notDefaultDb)) {
			JGitTestUtil.writeTrashFile(notDefaultDb
			git.add().addFilepattern("world.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(notDefaultDb);
		}
