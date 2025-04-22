                            if (store.isDefault(ThemeElementHelper
									.createPreferenceKey(Theme.this, fontDefinition.getId()))) {
                                getFontRegistry().put(fontDefinition.getId(), fd);
                                processDefaultsTo(fontDefinition.getId(), fd);
                            }
                        }
                    }
                }

                /**
                 * Process all colors that default to the given ID.
                 *
                 * @param key the color ID
                 * @param rgb the new RGB value for defaulted colors
                 */
                private void processDefaultsTo(String key, RGB rgb) {
