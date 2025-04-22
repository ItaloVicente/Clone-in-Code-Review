		showRelativeDateAction = new Action(
				UIText.ResourceHistory_toggleRelativeDate, IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				final boolean enable = isChecked();
				getPreferenceStore().setValue(
						UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE,
						enable);
			}
		};
		IPropertyChangeListener listener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(
						UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE)) {
					boolean showRelativeDate = (Boolean) event.getNewValue();
					getLabelProvider(stagedViewer).setShowRelativeDate(
							showRelativeDate);
					getLabelProvider(unstagedViewer).setShowRelativeDate(
							showRelativeDate);
					stagedViewer.refresh();
					unstagedViewer.refresh();
					return;
				}
			}
		};
		boolean showRelatvieDate = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE);
		showRelativeDateAction.setChecked(showRelatvieDate);
		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(listener);
		getLabelProvider(stagedViewer).setShowRelativeDate(showRelatvieDate);
		getLabelProvider(unstagedViewer).setShowRelativeDate(showRelatvieDate);

