		Map groupIDsToEntries = new HashMap();
		Map entryIDsToEntries = new HashMap();
		Map generatorExtensions = new HashMap();
		Set attributeMappings = new HashSet();
		processExtension(tracker, extension, groupIDsToEntries,
				entryIDsToEntries, attributeMappings, generatorExtensions);
		postProcessExtensions(groupIDsToEntries, entryIDsToEntries,
				attributeMappings, generatorExtensions);
