				if (rev != null) {
					if (compareMode) {
						ITypedElement right = CompareUtils
								.getFileRevisionTypedElement(gitPath, commit,
										map.getRepository());
						ITypedElement ancestor = CompareUtils.
								getFileRevisionTypedElementForCommonAncestor(
								gitPath, headCommit, commit, repo);
						final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
								SaveableCompareEditorInput
										.createFileElement(resource), right, ancestor,
								null);
						try {
							openInCompare(event, in);
						} catch (Exception e) {
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
				}
			}
		}
		if (input instanceof File) {
			File fileInput = (File) input;
			gitPath = getRepoRelativePath(repo, fileInput);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				IFileRevision rev = null;
