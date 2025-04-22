		}
		return updatedRGB;
	}

	private ColorDefinition[] getDescendantColors(ColorDefinition definition) {
		List list = new ArrayList(5);
		String id = definition.getId();

		ColorDefinition[] colors = themeRegistry.getColors();
		ColorDefinition[] sorted = new ColorDefinition[colors.length];
		System.arraycopy(colors, 0, sorted, 0, sorted.length);

		Arrays.sort(sorted, new IThemeRegistry.HierarchyComparator(colors));

		for (ColorDefinition colorDefinition : sorted) {
			if (id.equals(colorDefinition.getDefaultsTo()))
