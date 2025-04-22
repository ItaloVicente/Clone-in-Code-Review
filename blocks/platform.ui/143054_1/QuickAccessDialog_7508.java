		BusyIndicator.showWhile(window.getShell() == null ? null : window.getShell().getDisplay(), () -> {
			final CommandProvider commandProvider = new CommandProvider();
			commandProvider.setSnapshot(new ExpressionContext(model.getContext().getActiveLeaf()));
			List<QuickAccessProvider> providers = new ArrayList<>();
			providers.add(new PreviousPicksProvider(previousPicksList));
			providers.add(new EditorProvider());
			providers.add(new ViewProvider(model.getContext().get(MApplication.class), model));
			providers.add(new PerspectiveProvider());
			providers.add(commandProvider);
			providers.add(new ActionProvider());
			providers.add(new WizardProvider());
			providers.add(new PreferenceProvider());
			providers.add(new PropertiesProvider());
			providers.addAll(QuickAccessExtensionManager.getProviders(() -> {
				if (display != null) {
					display.asyncExec(() -> {
						QuickAccessDialog dialog = new QuickAccessDialog(window, invokingCommand);
						dialog.filterText.setText(lastSelectionFilter);
						dialog.open();
					});
				}
			}));
			providerMap = new HashMap<>();
			for (QuickAccessProvider provider : providers) {
				providerMap.put(provider.getId(), provider);
			}
			QuickAccessDialog.this.contents = new QuickAccessContents(
					providers.toArray(new QuickAccessProvider[providers.size()])) {
				@Override
				protected void updateFeedback(boolean filterTextEmpty, boolean showAllMatches) {
					TriggerSequence[] sequences = getInvokingCommandKeySequences();
					if (showAllMatches || sequences == null || sequences.length == 0) {
						setInfoText(""); //$NON-NLS-1$
					} else {
						setInfoText(NLS.bind(QuickAccessMessages.QuickAccess_PressKeyToShowAllMatches,
								sequences[0].format()));
