		try (Git git = new Git(groupBDb)) {
			JGitTestUtil.writeTrashFile(groupBDb
			git.add().addFilepattern("b.txt").call();
			git.commit().setMessage("Initial commit").call();
			addRepoToClose(groupBDb);
		}
