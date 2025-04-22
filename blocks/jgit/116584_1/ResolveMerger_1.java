			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

			int newMode = mergeFileModes(tw.getRawMode(0)
					tw.getRawMode(2));
			dce.setFileMode(newMode == FileMode.MISSING.getBits()
					? FileMode.REGULAR_FILE : FileMode.fromBits(newMode));
			if (mergedFile != null) {
				dce.setLastModified(
						nonNullRepo().getFS().lastModified(mergedFile));
				dce.setLength((int) mergedFile.length());
			}
			dce.setObjectId(insertMergeResult(rawMerged));
			builder.add(dce);
		} finally {
			if (rawMerged != null) {
				rawMerged.destroy();
