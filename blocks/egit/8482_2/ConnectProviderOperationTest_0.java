	@Test
	public void testAutoIgnoresDerivedFolder() throws CoreException, IOException {
		Repository repository = new FileRepository(gitDir);
		repository.create();
		repository.close();
		ConnectProviderOperation operation = new ConnectProviderOperation(
				project.getProject(), gitDir);
		operation.execute(null);

		assertTrue(RepositoryProvider.isShared(project.getProject()));
		IPath binPath = project.getProject().getLocation().append("bin");
		assertTrue(isIgnored(binPath));
		assertTrue(gitDir.exists());
	}

	private boolean isIgnored(IPath path) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		Repository repository = mapping.getRepository();
		String repoRelativePath = mapping.getRepoRelativePath(path);
		TreeWalk walk = new TreeWalk(repository);
		walk.addTree(new FileTreeIterator(repository));
		walk.setFilter(PathFilter.create(repoRelativePath));
		while (walk.next()) {
			WorkingTreeIterator workingTreeIterator = walk.getTree(0,
					WorkingTreeIterator.class);
			if (walk.getPathString().equals(repoRelativePath)) {
				return workingTreeIterator.isEntryIgnored();
			}
			if (workingTreeIterator.getEntryFileMode().equals(FileMode.TREE))
				walk.enterSubtree();
		}
		return false;
	}

