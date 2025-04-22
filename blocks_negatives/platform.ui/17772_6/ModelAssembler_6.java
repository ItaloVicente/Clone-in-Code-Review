		while (!sortedByOutdegree.isEmpty()) {
			if (!requires.get(sortedByOutdegree.get(0)).isEmpty()) {
				Collections.sort(sortedByOutdegree, outdegreeSorter);
			}
			String bundleId = sortedByOutdegree.remove(0);
			assert depends.containsKey(bundleId) && requires.containsKey(bundleId);
			for (IExtension ext : mappedExtensions.get(bundleId)) {
				extensions[resultIndex++] = ext;
			}
			assert requires.get(bundleId).isEmpty();
			requires.remove(bundleId);
			for (String depId : depends.get(bundleId)) {
				requires.get(depId).remove(bundleId);
