		} else {
			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
			int newMode = mergeFileModes(tw.getRawMode(0), tw.getRawMode(1),
					tw.getRawMode(2));
			dce.setFileMode((newMode == FileMode.MISSING.getBits()) ? FileMode.REGULAR_FILE
					: FileMode.fromBits(newMode));
			dce.setLastModified(of.lastModified());
			dce.setLength((int) of.length());
			InputStream is = new FileInputStream(of);
