	private final IPropertyChangeListener uiPrefsListener;

	private final AtomicReference<PreferenceBasedDateFormatter> dateFormatter = new AtomicReference<>();

	public GitChangeSetLabelProvider() {
		dateFormatter.set(PreferenceBasedDateFormatter.create());
		uiPrefsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)) {
					dateFormatter.set(PreferenceBasedDateFormatter.create());
					fireLabelProviderChanged(new LabelProviderChangedEvent(
							GitChangeSetLabelProvider.this));
				}

			}
		};
		store.addPropertyChangeListener(uiPrefsListener);
	}
