				if (((Boolean) e.data).booleanValue()
						&& commitCombo.getItemCount() == 0) {
					final RevWalk[] commits = new RevWalk[1];
					try {
						PlatformUI.getWorkbench().getProgressService()
								.busyCursorWhile(new IRunnableWithProgress() {

									public void run(IProgressMonitor monitor)
											throws InvocationTargetException,
											InterruptedException {
										commits[0] = getRevCommits();
									}
								});
					} catch (InvocationTargetException e1) {
					} catch (InterruptedException e1) {
					}
					for (RevCommit revCommit : commits[0])
						commitCombo.add(revCommit);
				}
				composite.layout(true);
