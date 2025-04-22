	private boolean isIgnored(IResource resource) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		Repository repository = mapping.getRepository();
		String path = mapping.getRepoRelativePath(resource);
		TreeWalk walk = new TreeWalk(repository);
		walk.addTree(new FileTreeIterator(repository));
		walk.setFilter(PathFilter.create(path));
		while (walk.next()) {
			WorkingTreeIterator workingTreeIterator = walk.getTree(0,
					WorkingTreeIterator.class);
			if (walk.getPathString().equals(path)) {
				return workingTreeIterator.isEntryIgnored();
			}
			if (workingTreeIterator.getEntryFileMode().equals(FileMode.TREE))
				walk.enterSubtree();
		}
		return false;
	}

