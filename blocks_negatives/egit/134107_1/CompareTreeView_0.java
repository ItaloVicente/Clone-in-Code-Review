				PathNode[] nodes = new PathNode[resources.length];
				for (int i = 0; i < resources.length; i++) {
					IResource resource = resources[i];
					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(resource));
					if (resource instanceof IFile)
						nodes[i] = fileNodes.get(path);
					else
						nodes[i] = containerNodes.get(path);
