							if (store.isDefault(
									ThemeElementHelper.createPreferenceKey(Theme.this, fontDefinition.getId()))) {
								getFontRegistry().put(fontDefinition.getId(), fd);
								processDefaultsTo(fontDefinition.getId(), fd);
							}
						}
					}
				}

				private void processDefaultsTo(String key, RGB rgb) {
