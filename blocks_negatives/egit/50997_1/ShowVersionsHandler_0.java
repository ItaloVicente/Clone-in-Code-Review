<<<<<<< Upstream, based on origin/master
=======
				if (rev != null) {
					if (compareMode) {
						ITypedElement right = CompareUtils
								.getFileRevisionTypedElement(commitPath,
										commit, map.getRepository());
						final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
								SaveableCompareEditorInput
										.createFileElement(resource), right,
								null);
						try {
							CompareUtils.openInCompare(workBenchPage,
									map.getRepository(), in);
						} catch (Exception e) {
							errorOccurred = true;
						}
					} else
						try {
							EgitUiEditorUtils.openEditor(getPart(event)
									.getSite().getPage(), rev,
									new NullProgressMonitor());
						} catch (CoreException e) {
							Activator.logError(
									UIText.GitHistoryPage_openFailed, e);
							errorOccurred = true;
						}
				} else
					ids.add(commit.getId());
