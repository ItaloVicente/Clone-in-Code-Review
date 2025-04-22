								shell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										TreeViewer viewer = navigator
												.getViewer();
										if (viewer != null
												&& viewer.getControl() != null
												&& !viewer.getControl()
														.isDisposed()) {
											viewer.refresh();
										}
