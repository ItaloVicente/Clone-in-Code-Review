					if (compareMode)
						try (RevWalk rw = new RevWalk(repo)) {
							ITypedElement left = CompareUtils
									.getFileRevisionTypedElement(gitPath,
											rw.parseCommit(repo
													.resolve(Constants.HEAD)),
											repo);
							ITypedElement right = CompareUtils
									.getFileRevisionTypedElement(commitPath,
											commit, repo);
							final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
									left, right, null);
							CompareUtils.openInCompare(workbenchPage, in);
						} catch (IOException e) {
							errorOccurred = true;
						}
					else
