	public static final int MOD_TIME = 1;

	public static final int SMUDGE = 2;

	public static final int LENGTH = 4;

	public static final int CONTENT_ID = 8;

	public static final int CONTENT = 16;

	public static final int ASSUME_UNCHANGED = 32;

	public static String indexState(Repository repo
			throws IllegalStateException
		DirCache dc = repo.readDirCache();
		StringBuilder sb = new StringBuilder();
		TreeSet<Long> timeStamps = null;

		if (0 != (includedOptions & MOD_TIME)) {
			timeStamps = new TreeSet<Long>();
			for (int i=0; i<dc.getEntryCount(); ++i)
				timeStamps.add(Long.valueOf(dc.getEntry(i).getLastModified()));
		}

		for (int i=0; i<dc.getEntryCount(); ++i) {
			DirCacheEntry entry = dc.getEntry(i);
			sb.append("["+entry.getPathString()+"
			int stage = entry.getStage();
			if (stage != 0)
				sb.append("
			if (0 != (includedOptions & MOD_TIME)) {
				sb.append("
						timeStamps.headSet(Long.valueOf(entry.getLastModified())).size());
			}
			if (0 != (includedOptions & SMUDGE))
				if (entry.isSmudged())
					sb.append("
			if (0 != (includedOptions & LENGTH))
				sb.append("
						+ Integer.toString(entry.getLength()));
			if (0 != (includedOptions & CONTENT_ID))
				sb.append("
			if (0 != (includedOptions & CONTENT)) {
				sb.append("
						+ new String(repo.open(entry.getObjectId()
						Constants.OBJ_BLOB).getCachedBytes()
			}
			if (0 != (includedOptions & ASSUME_UNCHANGED))
				sb.append("
						+ Boolean.toString(entry.isAssumeValid()));
			sb.append("]");
		}
		return sb.toString();
	}


