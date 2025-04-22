					RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
					try {
						setBaseEnabled(getRemoteConfig(node) != null);
					} catch (ExecutionException e) {
						Activator.logError(e.getMessage(), e);
						setBaseEnabled(false);
					}

