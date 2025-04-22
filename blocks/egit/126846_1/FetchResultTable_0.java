							.toArray()) {
						if (element instanceof RepositoryCommit) {
							CommitEditor.openQuiet((RepositoryCommit) element,
									activateOnOpen);
						} else if (element instanceof FileDiff) {
							CommitFileDiffViewer
									.showTwoWayFileDiff((FileDiff) element);
						}
					}
				}
