		Map<String, Collection<MarkerGroupingEntry>> groupIDsToEntries = new HashMap<>();
		Map<String, MarkerGroupingEntry> entryIDsToEntries = new HashMap<>();
		Map<String, Collection<IConfigurationElement>> generatorExtensions = new HashMap<>();
		Set<AttributeMarkerGrouping> attributeMappings = new HashSet<>();
		processExtension(tracker, extension, groupIDsToEntries,	entryIDsToEntries, attributeMappings, generatorExtensions);
		postProcessExtensions(groupIDsToEntries, entryIDsToEntries,	attributeMappings, generatorExtensions);
