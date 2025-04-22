				try (TreeWalk tw = TreeWalk.forPath(repository,
						repoRelativePath, headCommit.getTree())) {
					if (tw == null) {
						return new GitCompareFileRevisionEditorInput.EmptyTypedElement(
								NLS.bind(UIText.GitHistoryPage_FileNotInCommit,
										getName(repoRelativePath),
										Constants.HEAD));
					}
				}
