				if (delete) {
					try {
						for (RepositoryNode node : selectedNodes) {
							Repository repo = node.getRepository();
							if (!repo.isBare())
								deleteRecursive(repo.getWorkTree());
							deleteRecursive(repo.getDirectory());
						}
					} catch (IOException e) {
						return Activator.createErrorStatus(e.getMessage(), e);
					}
				}
