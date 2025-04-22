			op.setCredentialsProvider(new EGitCredentialsProvider());
			final PushOperationResult[] result = new PushOperationResult[1];
			getContainer().run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						result[0] = op.execute(monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
			PushResultDialog.show(repository, result[0],
					op.getDestinationString(), false, false);
