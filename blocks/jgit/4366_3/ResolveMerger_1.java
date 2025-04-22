	private void updateIndex(CanonicalTreeParser base
			CanonicalTreeParser ours
			MergeResult<RawText> result
			IOException {
		if (result.containsConflicts()) {
			add(tw.getRawPath()
			add(tw.getRawPath()
			add(tw.getRawPath()
			mergeResults.put(tw.getPathString()
		} else {
			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
			int newMode = mergeFileModes(tw.getRawMode(0)
					tw.getRawMode(2));
			dce.setFileMode((newMode == FileMode.MISSING.getBits()) ? FileMode.REGULAR_FILE
					: FileMode.fromBits(newMode));
			dce.setLastModified(of.lastModified());
			dce.setLength((int) of.length());
			InputStream is = new FileInputStream(of);
			try {
				dce.setObjectId(oi.insert(Constants.OBJ_BLOB
			} finally {
				is.close();
				if (inCore)
					FileUtils.delete(of);
			}
			builder.add(dce);
		}
	}
