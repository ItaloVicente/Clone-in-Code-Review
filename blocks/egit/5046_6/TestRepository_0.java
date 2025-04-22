	public void untrack(File file) throws IOException {
		String repoPath = getRepoRelativePath(new Path(file.getPath())
				.toString());
		try {
			new Git(repository).rm().addFilepattern(repoPath).call();
		} catch (NoFilepatternException e) {
			throw new IOException(e.getMessage());
		}
	}
