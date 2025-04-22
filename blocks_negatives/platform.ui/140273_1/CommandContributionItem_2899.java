		menuService = contributionParameters.serviceLocator
				.getService(IMenuService.class);
		commandService = contributionParameters.serviceLocator
				.getService(ICommandService.class);
		handlerService = contributionParameters.serviceLocator
				.getService(IHandlerService.class);
		bindingService = contributionParameters.serviceLocator
				.getService(IBindingService.class);
		IWorkbenchLocationService workbenchLocationService = contributionParameters.serviceLocator.getService(IWorkbenchLocationService.class);
