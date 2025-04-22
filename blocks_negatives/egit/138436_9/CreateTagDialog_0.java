					PlatformUI.getWorkbench().getDisplay()
							.asyncExec(new Runnable() {
								@Override
								public void run() {
									if (!tagViewer.getTable().isDisposed()) {
										tagViewer.setInput(tags);
										tagViewer.getTable().setEnabled(true);
									}
								}
							});
