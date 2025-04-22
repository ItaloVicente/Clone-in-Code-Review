				getWizard().getContainer().run(false, true,
						new IRunnableWithProgress() {
							/*
							 * (non-Javadoc)
							 *
							 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
							 */
							@Override
							public void run(IProgressMonitor monitor) {
								monitor
										.beginTask(
												MarkerMessages.MarkerResolutionDialog_Fixing,
												checked.length);
								for (int i = 0; i < checked.length; i++) {
									getShell().getDisplay().readAndDispatch();
									if (monitor.isCanceled())
										return;
									IMarker marker = (IMarker) checked[i];
									monitor.subTask(Util.getProperty(
											IMarker.MESSAGE, marker));
									resolution.run(marker);
									monitor.worked(1);
								}
