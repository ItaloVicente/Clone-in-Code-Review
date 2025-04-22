		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				RepositorySourceProvider provider = new RepositorySourceProvider();
				IEvaluationService evaluationService = PlatformUI.getWorkbench()
						.getService(IEvaluationService.class);
				IServiceLocator serviceLocator = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				provider.initialize(serviceLocator);
				evaluationService.addSourceProvider(provider);
			}
		});
