		propertyChangeListener = new IPropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == PreferenceConstants.PREF_SHOWDEBUG) {
					showDebug = UndoPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.PREF_SHOWDEBUG);
					viewer.refresh();
				}
