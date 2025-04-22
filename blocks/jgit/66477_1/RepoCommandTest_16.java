		try (Git git = new Git(groupADb)) {
			JGitTestUtil.writeTrashFile(groupADb
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(groupADb);
		}
