		propertyChangeListener = propertyChangeEvent -> {
			if (IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR
					.equals(propertyChangeEvent.getProperty())) {
				Object newValue = propertyChangeEvent.getNewValue();
				boolean showText = true; // default
				if (newValue instanceof Boolean)
					showText = ((Boolean) newValue).booleanValue();
				else if ("false".equals(newValue)) //$NON-NLS-1$
					showText = false;
				changeShowText(showText);
