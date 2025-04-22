	protected void checkNotNested() throws CoreException {
		final Collection<RepositoryMapping> parentRepositories = new RepositoryFinder(
				project.getProject()).find(null);
		final int numOfRepositories = parentRepositories.size();
		assertTrue("parent repository found: " + parentRepositories.toString(),
				numOfRepositories == 0);
	}

