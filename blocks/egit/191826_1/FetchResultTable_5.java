		treeViewer.setContentProvider(new BaseWorkbenchContentProvider() {

			private DeferredTreeContentManager loader = new DeferredTreeContentManager(
					treeViewer) {

				@Override
				protected void addChildren(Object parentItem, Object[] children,
						IProgressMonitor monitor) {
					WorkbenchJob updateJob = new WorkbenchJob(
							UIText.FetchResultTable_addingChildren) {

						@Override
						public IStatus runInUIThread(
								IProgressMonitor updateMonitor) {
							if (treeViewer.getControl().isDisposed()
									|| updateMonitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}
							treeViewer.add(parentItem, children);
							treeViewer.update(parentItem, null);
							if (children.length > 0) {
								int topItems = tree.getItemCount();
								if (topItems > 0 && parentItem == tree
										.getItem(0).getData()) {
									treeViewer.expandToLevel(parentItem, 1,
											true);
								}
							}
							return Status.OK_STATUS;
						}
					};
					updateJob.setSystem(true);
					updateJob.schedule();
				}
			};

			private Object currentInput;

