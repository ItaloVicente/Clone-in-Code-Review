		try (final TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.addTree(new AdaptableFileTreeIterator(repository, root));
			treeWalk.setRecursive(true);

			final IFile eclipseFile = project.getProject()
					.getFile(file.getName());
			final RepositoryMapping mapping = RepositoryMapping
					.getMapping(eclipseFile);
			final Set<String> repositoryPaths = Collections
					.singleton(mapping.getRepoRelativePath(eclipseFile));

			assertEquals(1, repositoryPaths.size());
			treeWalk.setFilter(
					PathFilterGroup.createFromStrings(repositoryPaths));

			assertTrue(treeWalk.next());

			final WorkingTreeIterator iterator = treeWalk.getTree(0,
					WorkingTreeIterator.class);
			assertTrue(iterator instanceof ContainerTreeIterator);
		}
