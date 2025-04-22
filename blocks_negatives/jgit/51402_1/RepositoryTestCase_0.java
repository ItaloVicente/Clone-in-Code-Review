	public static final int MOD_TIME = 1;

	public static final int SMUDGE = 2;

	public static final int LENGTH = 4;

	public static final int CONTENT_ID = 8;

	public static final int CONTENT = 16;

	public static final int ASSUME_UNCHANGED = 32;

	/**
	 * Represent the state of the index in one String. This representation is
	 * useful when writing tests which do assertions on the state of the index.
	 * By default information about path, mode, stage (if different from 0) is
	 * included. A bitmask controls which additional info about
	 * modificationTimes, smudge state and length is included.
	 * <p>
	 * The format of the returned string is described with this BNF:
	 *
	 * <pre>
	 * result = ( "[" path mode stage? time? smudge? length? sha1? content? "]" )* .
	 * mode = ", mode:" number .
	 * stage = ", stage:" number .
	 * time = ", time:t" timestamp-index .
	 * smudge = "" | ", smudged" .
	 * length = ", length:" number .
	 * sha1 = ", sha1:" hex-sha1 .
	 * content = ", content:" blob-data .
	 * </pre>
	 *
	 * 'stage' is only presented when the stage is different from 0. All
	 * reported time stamps are mapped to strings like "t0", "t1", ... "tn". The
	 * smallest reported time-stamp will be called "t0". This allows to write
	 * assertions against the string although the concrete value of the time
	 * stamps is unknown.
	 *
	 * @param repo
	 *            the repository the index state should be determined for
	 *
	 * @param includedOptions
	 *            a bitmask constructed out of the constants {@link #MOD_TIME},
	 *            {@link #SMUDGE}, {@link #LENGTH}, {@link #CONTENT_ID} and
	 *            {@link #CONTENT} controlling which info is present in the
	 *            resulting string.
	 * @return a string encoding the index state
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String indexState(Repository repo, int includedOptions)
			throws IllegalStateException, IOException {
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
			sb.append("["+entry.getPathString()+", mode:" + entry.getFileMode());
			int stage = entry.getStage();
			if (stage != 0)
				sb.append(", stage:" + stage);
			if (0 != (includedOptions & MOD_TIME)) {
				sb.append(", time:t"+
					timeStamps.headSet(Long.valueOf(entry.getLastModified())).size());
			}
			if (0 != (includedOptions & SMUDGE))
				if (entry.isSmudged())
					sb.append(", smudged");
			if (0 != (includedOptions & LENGTH))
				sb.append(", length:"
						+ Integer.toString(entry.getLength()));
			if (0 != (includedOptions & CONTENT_ID))
				sb.append(", sha1:" + ObjectId.toString(entry.getObjectId()));
			if (0 != (includedOptions & CONTENT)) {
				sb.append(", content:"
						+ new String(db.open(entry.getObjectId(),
								Constants.OBJ_BLOB).getCachedBytes(), "UTF-8"));
			}
			if (0 != (includedOptions & ASSUME_UNCHANGED))
				sb.append(", assume-unchanged:"
						+ Boolean.toString(entry.isAssumeValid()));
			sb.append("]");
		}
		return sb.toString();
	}

