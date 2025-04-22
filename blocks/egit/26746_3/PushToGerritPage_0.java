			final PushOperationResult[] result = new PushOperationResult[1];
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						result[0] = op.execute(monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
