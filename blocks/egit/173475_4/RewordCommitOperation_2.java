	public void execute(IProgressMonitor monitor) throws CoreException {
		try (RevWalk walk = new RevWalk(repository)) {
			DirCache index = repository.lockDirCache();
			try {
				reword(monitor, walk);
			} finally {
				index.unlock();
			}
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}
	}
