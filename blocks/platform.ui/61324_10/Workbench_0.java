				final IPropertyChangeListener themeToPreferencesFontSynchronizer = new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getNewValue() instanceof FontData[]) {
							FontData[] fontData = (FontData[]) event.getNewValue();
							PrefUtil.getInternalPreferenceStore().setValue(event.getProperty(),
									PreferenceConverter.getStoredRepresentation(fontData));
						}
					}
				};
				getThemeManager().getCurrentTheme().getFontRegistry().addListener(themeToPreferencesFontSynchronizer);
				getThemeManager().addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (IThemeManager.CHANGE_CURRENT_THEME.equals(event.getProperty())) {
							Object oldValue = event.getOldValue();
							if (oldValue != null && oldValue instanceof ITheme) {
								((ITheme) oldValue).removePropertyChangeListener(themeToPreferencesFontSynchronizer);
							}
							Object newValue = event.getNewValue();
							if (newValue != null && newValue instanceof ITheme) {
								((ITheme) newValue).addPropertyChangeListener(themeToPreferencesFontSynchronizer);
							}
						}
					}
				});
