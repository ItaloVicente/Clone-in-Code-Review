				if (((Boolean) e.data).booleanValue()
						&& commitCombo.getItemCount() == 0) {
					final Collection<RevCommit> commits = new ArrayList<RevCommit>();
					try {
						PlatformUI.getWorkbench().getProgressService()
								.busyCursorWhile(new IRunnableWithProgress() {

									public void run(IProgressMonitor monitor)
											throws InvocationTargetException,
											InterruptedException {
										getRevCommits(commits);
									}
								});
					} catch (InvocationTargetException e1) {
						Activator.logError(e1.getMessage(), e1);
					} catch (InterruptedException e1) {
					}
					for (RevCommit revCommit : commits)
						commitCombo.add(revCommit);
				}
				composite.layout(true);
