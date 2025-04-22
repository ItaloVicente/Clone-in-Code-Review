						for (RepositoryNode node : selectedNodes) {
							Repository repo = node.getRepository();
							repo.close();
							if (!repo.isBare() && deleteWorkDir)
								FileUtils.delete(repo.getWorkTree(),
										FileUtils.RECURSIVE | FileUtils.RETRY);
							FileUtils.delete(repo.getDirectory(),
									FileUtils.RECURSIVE | FileUtils.RETRY
											| FileUtils.SKIP_MISSING);
						}
