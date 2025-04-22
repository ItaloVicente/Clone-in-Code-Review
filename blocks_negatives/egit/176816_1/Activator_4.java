		Dictionary<String, String> props = new Hashtable<>(4);
		props.put(DebugOptions.LISTENER_SYMBOLICNAME, context.getBundle()
				.getSymbolicName());
		context.registerService(DebugOptionsListener.class.getName(), this,
				props);

		SelectionRepositoryStateCache.INSTANCE.initialize();
		setupRepoChangeScanner();
		setupFocusHandling();
		setupCredentialsProvider();
		ConfigurationChecker.checkConfiguration();

		registerTemplateVariableResolvers();
	}

	private void setupCredentialsProvider() {
