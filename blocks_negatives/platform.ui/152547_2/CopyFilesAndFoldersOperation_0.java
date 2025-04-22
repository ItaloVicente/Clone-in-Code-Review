		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				copyResources(resources, destinationPath, copiedResources,
						monitor);
			}
		};
