					IWorkbenchPage workBenchPage = HandlerUtil
							.getActiveWorkbenchWindowChecked(event)
							.getActivePage();
					if (input instanceof IFile) {
						CompareUtils.compareWorkspaceWithRef(repository,
								(IFile) input, commit.getId().getName(),
								workBenchPage);
