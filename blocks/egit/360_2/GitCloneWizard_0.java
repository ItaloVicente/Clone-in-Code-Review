				getContainer().run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						op.run(monitor);
					}
				});
