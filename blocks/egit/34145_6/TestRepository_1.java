	public void rm(File file) throws NoFilepatternException, GitAPIException {
		String repoPath = getRepoRelativePath(new Path(file.getPath())
				.toString());
		new Git(repository).rm().addFilepattern(repoPath).call();
	}

