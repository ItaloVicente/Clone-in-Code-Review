		this(true);
	}

	public CommitLabelProvider(final boolean canShowEmailAddresses) {
		super();
		store = Activator.getDefault().getPreferenceStore();
		uiPrefsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if ((UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property))
						&& (dateFormatter
								.get() instanceof PreferenceBasedDateFormatter)) {
					setDateFormatter(new PreferenceBasedDateFormatter());
				} else if (UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE
						.equals(property)) {
					setRelativeDate(store.getBoolean(
							UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE));
				} else if (canShowEmailAddresses
						&& UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES
								.equals(property)) {
					setShowEmailAddresses(store.getBoolean(
							UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES));
				}

			}
		};
		if (store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE)) {
			dateFormatter.set(
					new GitDateFormatter(GitDateFormatter.Format.RELATIVE));
		}
		showEmail = canShowEmailAddresses && store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES);
		store.addPropertyChangeListener(uiPrefsListener);
	}

	@Override
	public void dispose() {
		store.removePropertyChangeListener(uiPrefsListener);
		super.dispose();
