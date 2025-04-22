					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(resource));
					if (resource instanceof IFile)
						nodes[i] = fileNodes.get(path);
					else
						nodes[i] = containerNodes.get(path);
