		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor pm)
					throws InvocationTargetException {
				try {
					runCommand(pm);
				} catch (ExecutionException e) {
					if (pruning) {
						flush();
					}
					throw new InvocationTargetException(e);
				}
			}
		};
