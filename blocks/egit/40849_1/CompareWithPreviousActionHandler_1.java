					final PreviousCommit previous = getPreviousRevision(event,
							resources[0]);
					if (previous != null) {
						IWorkbenchPage workBenchPage = HandlerUtil
								.getActiveWorkbenchWindowChecked(event)
								.getActivePage();
						CompareUtils.compare(resources, repository,
								Constants.HEAD, previous.commit.getName(),
								true, workBenchPage);
					}
				} catch (Exception e) {
