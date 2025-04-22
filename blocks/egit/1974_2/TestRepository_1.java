	public void addToIndex(IFile file) throws CoreException, IOException {
		String repoPath = getRepoRelativePath(file.getLocation().toOSString());
		try {
			new Git(repository).add().addFilepattern(repoPath).call();
		} catch (NoFilepatternException e) {
			throw new IOException(e.getMessage());
		}
