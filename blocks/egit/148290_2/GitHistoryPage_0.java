					IPath path = repoNode.getPath();
					IResource resource = ResourceUtil
							.getResourceForLocation(path, false);
					if (resource != null) {
						input = new HistoryPageInput(repo,
								new IResource[] { resource });
					} else {
						File file = (File) repoNode.getObject();
						input = new HistoryPageInput(repo, new File[] { file });
					}
