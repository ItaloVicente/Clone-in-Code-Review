
	/**
	 * Writes a number of files in the working tree. The first content specified
	 * will be written into a file named '0', the second into a file named "1"
	 * and so on. If <code>null</code> is specified as content then this file is
	 * skipped.
	 *
	 * @param ensureDistinctTimestamps
	 *            if set to <code>true</code> then between two write operations
	 *            this method will wait to ensure that the second file will get
	 *            a different lastmodification timestamp than the first file.
	 * @param contents
	 *            the contents which should be written into the files
	 * @return the File object associated to the last written file.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected File writeTrashFiles(boolean ensureDistinctTimestamps,
			String... contents)
			throws IOException, InterruptedException {
				File f = null;
				for (int i = 0; i < contents.length; i++)
					if (contents[i] != null) {
						if (ensureDistinctTimestamps && (f != null))
							fsTick(f);
						f = writeTrashFile(Integer.toString(i), contents[i]);
					}
				return f;
			}
