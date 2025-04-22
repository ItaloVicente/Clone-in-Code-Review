	private void createContentsAndTable() {
		if (quickAccessContents != null) {
			return;
		}
		final CommandProvider commandProvider = new CommandProvider();
		txtQuickAccess.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService hs = SearchField.this.window.getContext().get(IHandlerService.class);
				if (commandProvider.getContextSnapshot() == null) {
					commandProvider.setSnapshot(hs.createContextSnapshot(true));
				}
			}
		});
		List<QuickAccessProvider> providers = new ArrayList<>();
		providers.add(new PreviousPicksProvider(previousPicksList));
		providers.add(new EditorProvider());
		providers.add(new ViewProvider(application, window));
		providers.add(new PerspectiveProvider());
		providers.add(commandProvider);
		providers.add(new ActionProvider());
		providers.add(new WizardProvider());
		providers.add(new PreferenceProvider());
		providers.add(new PropertiesProvider());
		providers.addAll(QuickAccessExtensionManager.getProviders(() -> {
			txtQuickAccess.getDisplay().asyncExec(() -> {
				txtQuickAccess.setText(lastSelectionFilter);
				txtQuickAccess.setFocus();
				SearchField.this.showList();
			});
		}));

		quickAccessContents = new QuickAccessContents(providers.toArray(new QuickAccessProvider[providers.size()])) {
