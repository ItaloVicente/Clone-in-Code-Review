	/**
	 * Waits until it is guaranteed that the filesystem timer (used e.g. for
	 * lastModified) has a value greater than the lastmodified time of the given
	 * file. This is done by touch a file, reading the lastmodified and sleeping
	 * attribute sleeping
	 *
	 * @param lastFile
	 * @return return the last measured value of the filesystem timer which is
	 *         greater than then the lastmodification time of lastfile.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static long fsTick(File lastFile) throws InterruptedException,
			IOException {
		long sleepTime = 1;
		File tmp = File.createTempFile("FileTreeIteratorWithTimeControl", null);
		try {
			long startTime = (lastFile == null) ? tmp.lastModified() : lastFile
					.lastModified();
			long actTime = tmp.lastModified();
			while (actTime <= startTime) {
				Thread.sleep(sleepTime);
				sleepTime *= 5;
				tmp.setLastModified(System.currentTimeMillis());
				actTime = tmp.lastModified();
			}
			return actTime;
		} finally {
			tmp.delete();
		}
	}

