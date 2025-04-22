					IWorkbenchPage workBenchPage = HandlerUtil
							.getActiveWorkbenchWindowChecked(event)
							.getActivePage();
					if (input instanceof IFile)
						CompareUtils.compareWorkspaceWithRef(repository,
								(IFile) input, commit.getId().getName(),
								workBenchPage);
					else
						CompareUtils.compareLocalWithRef(repository,
								(File) input, commit.getId().getName(),
								workBenchPage);
