	 *
	 * @param base
	 * @param ours
	 * @param theirs
	 * @param result
	 * @param attributes
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void updateIndex(CanonicalTreeParser base,
			CanonicalTreeParser ours, CanonicalTreeParser theirs,
			MergeResult<RawText> result, Attributes attributes)
			throws FileNotFoundException,
			IOException {
		TemporaryBuffer rawMerged = null;
		try {
			rawMerged = doMerge(result);
			File mergedFile = inCore ? null
					: writeMergedFile(rawMerged, attributes);
			if (result.containsConflicts()) {
				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);
				mergeResults.put(tw.getPathString(), result);
				return;
			}

			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

			int newMode = mergeFileModes(tw.getRawMode(0), tw.getRawMode(1),
					tw.getRawMode(2));
			dce.setFileMode(newMode == FileMode.MISSING.getBits()
					? FileMode.REGULAR_FILE : FileMode.fromBits(newMode));
			if (mergedFile != null) {
				dce.setLastModified(
						nonNullRepo().getFS().lastModifiedInstant(mergedFile));
				dce.setLength((int) mergedFile.length());
			}
			dce.setObjectId(insertMergeResult(rawMerged, attributes));
			builder.add(dce);
		} finally {
			if (rawMerged != null) {
				rawMerged.destroy();
			}
		}
	}

	/**
	 * Writes merged file content to the working tree.
	 *
