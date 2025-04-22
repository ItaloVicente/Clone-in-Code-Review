				List<PathNode> nodes = new ArrayList<>(resources.length);
				for (IResource resource : resources) {
					if (resource == null) {
						continue;
					}
					String repoRelative = repositoryMapping
							.getRepoRelativePath(resource);
					if (repoRelative == null) {
						continue;
					}
					IPath path = new Path(repoRelative);
					PathNode node;
					if (resource instanceof IFile) {
						node = fileNodes.get(path);
					} else {
						node = containerNodes.get(path);
					}
					if (node != null) {
						nodes.add(node);
					}
