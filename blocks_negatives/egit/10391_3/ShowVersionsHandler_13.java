				if (rev != null) {
					if (compareMode) {
						try {
							ITypedElement left = CompareUtils
									.getFileRevisionTypedElement(gitPath,
											new RevWalk(repo).parseCommit(repo
													.resolve(Constants.HEAD)),
											repo);
							ITypedElement right = CompareUtils
									.getFileRevisionTypedElement(gitPath,
											commit, repo);
							ITypedElement ancestor = CompareUtils.
									getFileRevisionTypedElementForCommonAncestor(
									gitPath, headCommit, commit, repo);
							final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
									left, right, ancestor, null);
							openInCompare(event, in);
						} catch (IOException e) {
							errorOccurred = true;
						}
					} else {
						try {
							EgitUiEditorUtils.openEditor(getPart(event)
									.getSite().getPage(), rev,
									new NullProgressMonitor());
						} catch (CoreException e) {
							Activator.logError(
									UIText.GitHistoryPage_openFailed, e);
							errorOccurred = true;
						}
					}
				} else {
					ids.add(commit.getId());
