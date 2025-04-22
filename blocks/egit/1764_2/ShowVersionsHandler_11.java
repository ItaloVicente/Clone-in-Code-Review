					ids.add(commit.getId());
				}
			}
		}
		if (input instanceof File) {
			File fileInput = (File) input;
			Repository repo = getRepository(event);
			gitPath = getRepoRelativePath(repo, fileInput);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				IFileRevision rev = null;
				try {
					rev = CompareUtils.getFileRevision(gitPath, commit, repo,
							null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
					errorOccured = true;
				}
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
							final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
									left, right, null);
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
