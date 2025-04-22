					QuickAccessProvider[] providers = new QuickAccessProvider[] {
							new PreviousPicksProvider(previousPicksList),
							new EditorProvider(),
							new ViewProvider(model.getContext().get(MApplication.class), model),
							new PerspectiveProvider(), commandProvider, new ActionProvider(),
							new WizardProvider(), new PreferenceProvider(),
							new PropertiesProvider() };
