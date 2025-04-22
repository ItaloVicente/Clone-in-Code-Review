			int rightTreeIndex = tw.addTree(new CanonicalTreeParser(null,
					repository.newObjectReader(), rightCommit.getTree()));
			if (resource instanceof IResource) {
				String relPath = repositoryMapping
						.getRepoRelativePath((IResource) resource);
				if (relPath.length() > 0)
					tw.setFilter(PathFilter.create(relPath));
