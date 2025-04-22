			return;
		}

		DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

		int newMode = mergeFileModes(
				tw.getRawMode(0)
				tw.getRawMode(1)
				tw.getRawMode(2));
		dce.setFileMode(newMode == FileMode.MISSING.getBits()
				? FileMode.REGULAR_FILE
				: FileMode.fromBits(newMode));
		if (mergedFile != null) {
			long len = mergedFile.length();
			dce.setLastModified(mergedFile.lastModified());
			dce.setLength((int) len);
			InputStream is = new FileInputStream(mergedFile);
