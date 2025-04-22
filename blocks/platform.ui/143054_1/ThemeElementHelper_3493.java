			}
		}
		return set;
	}


	private static void installColor(ColorDefinition definition, ITheme theme, IPreferenceStore store,
			boolean setInRegistry) {


		ColorRegistry registry = theme.getColorRegistry();

		String id = definition.getId();
		String key = createPreferenceKey(theme, id);
		RGB prefColor = store != null ? PreferenceConverter.getColor(store, key) : null;
