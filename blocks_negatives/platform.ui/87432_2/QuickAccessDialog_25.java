				new Runnable() {

					@Override
					public void run() {
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
						for (int i = 0; i < providers.length; i++) {
							providerMap.put(providers[i].getId(), providers[i]);
						}
						QuickAccessDialog.this.contents = new QuickAccessContents(providers) {
							@Override
							protected void updateFeedback(boolean filterTextEmpty,
									boolean showAllMatches) {
								if (filterTextEmpty) {
									setInfoText(QuickAccessMessages.QuickAccess_StartTypingToFindMatches);
