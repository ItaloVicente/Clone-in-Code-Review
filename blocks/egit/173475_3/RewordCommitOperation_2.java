	public void execute(IProgressMonitor monitor) throws CoreException {
		try (RevWalk walk = new RevWalk(repository)) {
			reword(monitor, walk);
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}
	}
