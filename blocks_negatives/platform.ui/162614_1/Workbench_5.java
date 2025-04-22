		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		preferenceStore.addPropertyChangeListener(event -> {
			if (IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS.equals(event.getProperty())) {
				Object o = event.getNewValue();
				if (o instanceof Boolean) {
					e4Context.set(IPresentationEngine.ANIMATIONS_ENABLED, o);
				} else if (o instanceof String) {
					e4Context.set(IPresentationEngine.ANIMATIONS_ENABLED,
							Boolean.parseBoolean((String) event.getNewValue()));
				}
			}
		});

