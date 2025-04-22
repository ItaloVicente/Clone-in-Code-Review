		final CommandProvider commandProvider = new CommandProvider();
		QuickAccessProvider[] providers = new QuickAccessProvider[] {
				new PreviousPicksProvider(previousPicksList),
				new EditorProvider(), new ViewProvider(application, window),
				new PerspectiveProvider(), commandProvider, new ActionProvider(),
				new WizardProvider(), new PreferenceProvider(), new PropertiesProvider() };
		for (QuickAccessProvider provider : providers) {
			providerMap.put(provider.getId(), provider);
		}
		restoreDialog();
