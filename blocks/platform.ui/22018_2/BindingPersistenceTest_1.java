		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		bindingService.readRegistryAndPreferences(null);
		final Scheme activeScheme = bindingService
				.getScheme(IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID);
		final Binding[] originalBindings = bindingService.getBindings();
		bindingService.savePreferences(activeScheme, originalBindings);
