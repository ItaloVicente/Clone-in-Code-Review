			int rightTreeIndex;
			if (!useIndex)
				rightTreeIndex = tw.addTree(new CanonicalTreeParser(null,
						repository.newObjectReader(), rightCommit.getTree()));
			else
				rightTreeIndex = tw.addTree(new DirCacheIterator(repository
						.readDirCache()));

			if (input instanceof IResource[]) {
				IResource[] resources = (IResource[]) input;
				List<TreeFilter> orFilters = new ArrayList<TreeFilter>(
						resources.length);

				for (IResource resource : resources) {
					String relPath = repositoryMapping
							.getRepoRelativePath(resource);
					if (relPath.length() > 0)
						orFilters.add(PathFilter.create(relPath));
				}
				if (orFilters.size() > 1)
					tw.setFilter(OrTreeFilter.create(orFilters));
				else if (orFilters.size() == 1)
					tw.setFilter(orFilters.get(0));
