				ResourceTraversal[] traversals = getScopeManager().getScope()
						.getTraversals();
				try {
					subscriber.refresh(traversals, new NullProgressMonitor());
				} catch (CoreException e) {
					Activator
							.error(CoreText.GitSubscriberMergeContext_FailedRefreshSyncView,
									e);
				}

				return;
