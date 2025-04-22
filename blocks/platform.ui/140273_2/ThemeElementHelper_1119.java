		}

		copyOfDefinitions = new FontDefinition[definitions.length];
		System.arraycopy(definitions, 0, copyOfDefinitions, 0, definitions.length);
		Arrays.sort(copyOfDefinitions, new IThemeRegistry.HierarchyComparator(definitions));

		for (FontDefinition definition : copyOfDefinitions) {
			installFont(definition, theme, store, true);
		}

		if (defaults != null) {
			for (FontDefinition fontDef : defaults) {
				installFont(fontDef, theme, store, false);
			}
		}
	}

	private static FontDefinition[] addDefaulted(FontDefinition[] definitions) {
		IThemeRegistry registry = WorkbenchPlugin.getDefault().getThemeRegistry();
		FontDefinition[] allDefs = registry.getFonts();

		SortedSet set = addDefaulted(definitions, allDefs);
		return (FontDefinition[]) set.toArray(new FontDefinition[set.size()]);
	}

	private static void installFont(FontDefinition definition, ITheme theme, IPreferenceStore store,
			boolean setInRegistry) {
		FontRegistry registry = theme.getFontRegistry();
