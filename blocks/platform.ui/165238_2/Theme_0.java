	private void registryColorChangeEvent(PropertyChangeEvent event) {
		if (event.getNewValue() instanceof RGB) {
			String key = ThemeElementHelper.createPreferenceKey(this, event.getProperty());
			IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
			if (store.contains(key)) {
				RGB newColor = (RGB) event.getNewValue();
				if (store.isDefault(key)) {
					RGB oldColor = PreferenceConverter.getDefaultColor(store, key);
					if (oldColor == PreferenceConverter.COLOR_DEFAULT_DEFAULT) {
					} else if (!newColor.equals(oldColor))
						PreferenceConverter.setValue(store, key, newColor);
				} else {
					RGB oldColor = PreferenceConverter.getColor(store, key);
					if (!newColor.equals(oldColor)) {
						oldColor = PreferenceConverter.getDefaultColor(store, key);
						if (oldColor != PreferenceConverter.COLOR_DEFAULT_DEFAULT && newColor.equals(oldColor))
							store.setToDefault(key);
						else
							PreferenceConverter.setValue(store, key, newColor);
					}
				}
			}
		}
	}

