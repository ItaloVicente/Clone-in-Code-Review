								shell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										StructuredViewer viewer = getActionSite().getStructuredViewer();
										if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
											viewer.refresh();
										}
