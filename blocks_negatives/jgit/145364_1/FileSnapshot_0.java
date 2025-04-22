	/**
	 * Record a snapshot for a specific file path.
	 * <p>
	 * This method should be invoked before the file is accessed.
	 *
	 * @param path
	 *            the path to later remember. The path's current status
	 *            information is saved.
	 * @return the snapshot.
	 */
	public static FileSnapshot save(File path) {
		return new FileSnapshot(path);
	}

	/**
	 * Record a snapshot for a specific file path.
	 * <p>
	 * This method should be invoked before the file is accessed.
	 *
	 * @param path
	 *            the path to later remember. The path's current status
	 *            information is saved.
	 * @param timestampResolutions
	 *            cache for file system timestamp resolution, to improve
	 *            performance for operations which are creating many snapshots
	 *            at once.
	 *
	 * @return the snapshot.
	 */
	public static FileSnapshot save(File path,
			FileSnapshotTimestampResolutions timestampResolutions) {
		return new FileSnapshot(path, timestampResolutions);
	}

