				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							public void run() {
								if (form.isDisposed())
									return;

								final IndexDiff indexDiff = results.get();
								unstagedTableViewer.setInput(new Object[] {
										repository, indexDiff });
								stagedTableViewer.setInput(new Object[] {
										repository, indexDiff });
								commitAction.setEnabled(repository
										.getRepositoryState().canCommit());
								form.setText(StagingView
										.getRepositoryName(repository));
								if (repositoryChanged) {
									updateCommitMessageComponent(repositoryChanged);
									clearCommitMessageToggles();
								}
								updateSectionText();
							}
