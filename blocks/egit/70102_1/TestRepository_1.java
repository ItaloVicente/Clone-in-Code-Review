	public void removeFromIndex(File file) throws NoFilepatternException, GitAPIException {
		String repoPath = getRepoRelativePath(new Path(file.getPath())
				.toString());
		try (Git git = new Git(repository)) {
			git.rm().addFilepattern(repoPath).call();
		}
	}

