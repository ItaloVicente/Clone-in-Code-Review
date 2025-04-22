			final Repository repository, final String gitPath,
			final ITypedElement base, final String refName) throws IOException {

		final GitCompareFileRevisionEditorInput[] inputs = new GitCompareFileRevisionEditorInput[1];
		try {
			new ProgressMonitorDialog(getShell()).run(true, false,
					new IRunnableWithProgress() {

						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							ITypedElement destCommit = null;
							ITypedElement commonAncestor = null;
							try {
								if (GitFileRevision.INDEX.equals(refName))
									destCommit = getIndexTypedElement(
											repository, gitPath);
								else if (Constants.HEAD.equals(refName))
									destCommit = getHeadTypedElement(
											repository, gitPath);
								else {
									final ObjectId destCommitId = repository
											.resolve(refName);
									RevWalk rw = new RevWalk(repository);
									RevCommit commit = rw
											.parseCommit(destCommitId);
									rw.release();
									destCommit = getFileRevisionTypedElement(
											gitPath, commit, repository);

									if (base != null && commit != null) {
										final ObjectId headCommitId = repository
												.resolve(Constants.HEAD);
										commonAncestor = getFileRevisionTypedElementForCommonAncestor(
												gitPath, headCommitId,
												destCommitId, repository);
									}
								}
								final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
										base, destCommit, commonAncestor, null);
								in.getCompareConfiguration().setRightLabel(
										refName);
								inputs[0] = in;
							} catch (IOException e) {
								org.eclipse.egit.ui.Activator.error(
										e.getMessage(), e);
							}
						}
