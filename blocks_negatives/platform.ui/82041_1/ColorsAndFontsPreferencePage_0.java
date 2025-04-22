
                    if (!ColorsAndFontsPreferencePage.equals(def.getCategoryId(), myCategory)) {
                    	if (isDefault(themeElement))
							return MessageFormat.format(RESOURCE_BUNDLE.getString("defaultFormat_default"), themeElement.getName(), def.getName() ); //$NON-NLS-1$
               			return MessageFormat.format(RESOURCE_BUNDLE.getString("defaultFormat_override"), themeElement.getName(), def.getName() ); //$NON-NLS-1$
                    }
                }
