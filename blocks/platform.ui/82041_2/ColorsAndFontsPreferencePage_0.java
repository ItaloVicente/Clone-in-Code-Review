					}
					if (def != null && !ColorsAndFontsPreferencePage.equals(def.getCategoryId(), myCategory)) {
						if (isDefault(themeElement)) {
							return MessageFormat.format(RESOURCE_BUNDLE.getString("defaultFormat_default"), //$NON-NLS-1$
									themeElement.getName(), def.getName());
						}
						return MessageFormat.format(RESOURCE_BUNDLE.getString("defaultFormat_override"), //$NON-NLS-1$
								themeElement.getName(), def.getName());
					}
				}
