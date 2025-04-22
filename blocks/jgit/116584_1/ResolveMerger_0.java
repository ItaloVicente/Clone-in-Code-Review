		TemporaryBuffer rawMerged = null;
		try {
			rawMerged = doMerge(result);
			File mergedFile = inCore ? null : writeMergedFile(rawMerged);
			if (result.containsConflicts()) {
				add(tw.getRawPath()
				add(tw.getRawPath()
				add(tw.getRawPath()
				mergeResults.put(tw.getPathString()
				return;
