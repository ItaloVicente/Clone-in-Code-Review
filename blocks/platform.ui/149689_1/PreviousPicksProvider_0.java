	PreviousPicksProvider(int maxNumberOfElements) {
		this.maxNumberOfElements = maxNumberOfElements;
	}

	public void setElementsInitializer(Supplier<List<QuickAccessElement>> initializer) {
		this.initializer = initializer;
	}

	public void setInvolvedProviders(Collection<QuickAccessProvider> providers) {
		this.initialProviders = providers;
