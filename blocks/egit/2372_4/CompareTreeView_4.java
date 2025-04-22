				if (adapter instanceof IFile) {
					IFile file = (IFile) adapter;
					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(file));
					if (addedPaths.contains(path))
						return ADDEDCATEGORY;
					if (equalContentPaths.contains(path))
						return UNCHANGEDCATEGORY;
					return CHANGEDCATEGORY;
