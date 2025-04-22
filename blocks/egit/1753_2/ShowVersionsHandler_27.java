					if (rev != null) {
						if (compareMode) {
							ITypedElement right = CompareUtils
									.getFileRevisionTypedElement(gitPath,
											commit, repo);
							final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
									SaveableCompareEditorInput
											.createFileElement(resource),
									right, null);
							try {
								openInCompare(event, in);
							} catch (Exception e) {
								errorOccured = true;
							}
						} else {
							try {
								EgitUiEditorUtils.openEditor(getPart(event)
										.getSite().getPage(), rev,
										new NullProgressMonitor());
							} catch (CoreException e) {
								Activator.logError(
										UIText.GitHistoryPage_openFailed, e);
								errorOccured = true;
							}
						}
					} else {
						ids.add(commit.getId());
					}
