			if (input instanceof IResource[]) {
				IResource[] resources = (IResource[]) input;
				PathNode[] nodes = new PathNode[resources.length];
				for (int i = 0; i < resources.length; i++) {
					IResource resource = resources[i];
					if (resource instanceof IFile) {
						IPath path = new Path(repositoryMapping
								.getRepoRelativePath(resource));
						Type type;
						if (addedPaths.contains(path))
							type = Type.FILE_ADDED;
						else if (equalContentPaths.contains(path))
							type = Type.FILE_BOTH_SIDES_SAME;
						else
							type = Type.FILE_BOTH_SIDES_DIFFER;
						nodes[i] = new PathNode(path, type);
					} else
						nodes[i] = new PathNode(new Path(repositoryMapping
								.getRepoRelativePath(resource)), Type.FOLDER);
				}
				return nodes;
			}
			return new PathNode[] { new PathNode(new Path(""), Type.FOLDER) }; //$NON-NLS-1$
