
		graphLabelProvider = new GraphLabelProvider();
		listener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(
						UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE)) {
					if (setRelativeDate(((Boolean) event.getNewValue())
							.booleanValue()) && (historyPage != null))
						historyPage.initAndStartRevWalk(true);
					return;
				}
			}
		};

		setRelativeDate(Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE));
		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(listener);

		table.setLabelProvider(graphLabelProvider);
