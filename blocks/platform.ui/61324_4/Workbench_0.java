				getThemeManager().getCurrentTheme().getFontRegistry().addListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getNewValue() instanceof FontData[]) {
							FontData[] fontData = (FontData[]) event.getNewValue();
							PrefUtil.getInternalPreferenceStore().setValue(event.getProperty(),
									PreferenceConverter.getStoredRepresentation(fontData));
						}
					}
				});
