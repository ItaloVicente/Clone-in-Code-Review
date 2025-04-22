			}
		}
	}

	private static ColorDefinition[] getDefaults(ColorDefinition[] definitions) {
		IThemeRegistry registry = WorkbenchPlugin.getDefault().getThemeRegistry();
		ColorDefinition[] allDefs = registry.getColors();

		SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
		set.addAll(Arrays.asList(allDefs));
		set.removeAll(Arrays.asList(definitions));
		return (ColorDefinition[]) set.toArray(new ColorDefinition[set.size()]);
	}

	private static FontDefinition[] getDefaults(FontDefinition[] definitions) {
		IThemeRegistry registry = WorkbenchPlugin.getDefault().getThemeRegistry();
		FontDefinition[] allDefs = registry.getFonts();

		SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
		set.addAll(Arrays.asList(allDefs));
		set.removeAll(Arrays.asList(definitions));
		return (FontDefinition[]) set.toArray(new FontDefinition[set.size()]);
	}

	private static ColorDefinition[] addDefaulted(ColorDefinition[] definitions) {
		IThemeRegistry registry = WorkbenchPlugin.getDefault().getThemeRegistry();
		ColorDefinition[] allDefs = registry.getColors();

		SortedSet set = addDefaulted(definitions, allDefs);
		return (ColorDefinition[]) set.toArray(new ColorDefinition[set.size()]);
	}

	private static SortedSet addDefaulted(IHierarchalThemeElementDefinition[] definitions,
			IHierarchalThemeElementDefinition[] allDefs) {
		SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
		set.addAll(Arrays.asList(definitions));

		IHierarchalThemeElementDefinition copy[] = new IHierarchalThemeElementDefinition[allDefs.length];
