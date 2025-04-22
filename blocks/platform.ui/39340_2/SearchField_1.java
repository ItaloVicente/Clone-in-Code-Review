
		List<IQuickAccessProvider> providerList = new ArrayList<IQuickAccessProvider>();
		providerList.add(new PreviousPicksProvider(previousPicksList));
		providerList.add(new EditorProvider());
		providerList.add(new ViewProvider(application, window));
		providerList.add(new PerspectiveProvider());
		providerList.add(commandProvider);
		providerList.add(new ActionProvider());
		providerList.add(new WizardProvider());
		providerList.add(new PreferenceProvider());
		providerList.add(new PropertiesProvider());

		addExtensionProviders(providerList, window.getContext());

		IQuickAccessProvider[] providers = providerList.toArray(new IQuickAccessProvider[providerList.size()]);

