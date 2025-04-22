		File mergedFile = !inCore ? writeMergedFile(result) : null;

		if (result.containsConflicts()) {
			add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, 0, 0);
			add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, 0, 0);
			add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, 0, 0);
			mergeResults.put(tw.getPathString(), result);
			return;
		}

		DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

		int newMode = mergeFileModes(
				tw.getRawMode(0),
				tw.getRawMode(1),
				tw.getRawMode(2));
		dce.setFileMode(newMode == FileMode.MISSING.getBits()
				? FileMode.REGULAR_FILE
				: FileMode.fromBits(newMode));
		if (mergedFile != null) {
			long len = mergedFile.length();
			dce.setLastModified(FS.DETECTED.lastModified(mergedFile));
			dce.setLength((int) len);
			EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKIN_OP, workingTreeOptions,
					tw.getAttributes());
			long blobLen = len == 0 ? 0
					: getEntryContentLength(mergedFile, streamType);
			try (InputStream is = EolStreamTypeUtil.wrapInputStream(
					new FileInputStream(mergedFile), streamType)) {
				dce.setObjectId(
						getObjectInserter().insert(OBJ_BLOB, blobLen, is));
