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
							CompareUtils.openInCompare(workBenchPage, in);
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
			}
		}
		if (input instanceof File) {
			File fileInput = (File) input;
			Repository repo = getRepository(event);
			gitPath = getRepoRelativePath(repo, fileInput);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				String commitPath = getRenamedPath(gitPath, commit);
				IFileRevision rev = null;
