				childList.add(child);
			}
			if (element instanceof IContainer) {
				List<PathNodeAdapter> deletedChildren = rightPathsWithChildren
						.get(new Path(repositoryMapping
								.getRepoRelativePath((IResource) element)));
				if (deletedChildren != null) {
