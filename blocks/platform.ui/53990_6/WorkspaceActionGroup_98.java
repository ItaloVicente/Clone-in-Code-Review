								shell.getDisplay().asyncExec(() -> {
									TreeViewer viewer = navigator
											.getViewer();
									if (viewer != null
											&& viewer.getControl() != null
											&& !viewer.getControl()
													.isDisposed()) {
										viewer.refresh();
