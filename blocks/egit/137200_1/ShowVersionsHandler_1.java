					if (compareMode) {
						ITypedElement right = CompareUtils
								.getFileRevisionTypedElement(commitPath, commit,
										repo);
						GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
								new LocalNonWorkspaceTypedElement(repo,
										new Path(fileInput.getAbsolutePath())),
								right, null);
						CompareUtils.openInCompare(workbenchPage, in);
					} else {
