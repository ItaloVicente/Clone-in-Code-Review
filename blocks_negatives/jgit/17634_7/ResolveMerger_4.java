			MergeResult<RawText> result, File of) throws FileNotFoundException,
			IOException {
		if (result.containsConflicts()) {
			add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, 0, 0);
			add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, 0, 0);
			add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, 0, 0);
			mergeResults.put(tw.getPathString(), result);
