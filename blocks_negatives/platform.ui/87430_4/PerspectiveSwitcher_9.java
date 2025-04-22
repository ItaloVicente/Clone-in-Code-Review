		propertyChangeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				if (IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR
						.equals(propertyChangeEvent.getProperty())) {
					Object newValue = propertyChangeEvent.getNewValue();
					if (newValue instanceof Boolean)
						showText = ((Boolean) newValue).booleanValue();
						showText = false;
					changeShowText(showText);
				}
