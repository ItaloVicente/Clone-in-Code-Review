						public void run() {
							for (TreeItem item : getCommonViewer().getTree()
									.getItems()) {
								oldInput.add((RepositoryNode) item.getData());
							}
						}
					});
				}
