					wp.getSite().getWorkbenchWindow().run(true, false,
							new IRunnableWithProgress() {
								public void run(final IProgressMonitor monitor)
										throws InvocationTargetException {
									try {
										op.execute(monitor);
									} catch (CoreException ce) {
										throw new InvocationTargetException(ce);
									}
								}
							});
				} finally {
