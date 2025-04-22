		BusyIndicator.showWhile(window.getShell() == null ? null : window.getShell().getDisplay(),
				() -> {
					final CommandProvider commandProvider = new CommandProvider();
					commandProvider.setSnapshot(new ExpressionContext(model.getContext()
							.getActiveLeaf()));
					QuickAccessProvider[] providers = new QuickAccessProvider[] {
							new PreviousPicksProvider(previousPicksList),
							new EditorProvider(),
							new ViewProvider(model.getContext().get(MApplication.class), model),
							new PerspectiveProvider(), commandProvider, new ActionProvider(),
							new WizardProvider(), new PreferenceProvider(),
							new PropertiesProvider() };
					providerMap = new HashMap();
					for (QuickAccessProvider provider : providers) {
						providerMap.put(provider.getId(), provider);
