	/**
	 * Write the given heads to a file in the git directory.
	 *
	 * @param heads
	 *            a list of object ids to write or null if the file should be
	 *            deleted.
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeHeadsFile(List<ObjectId> heads, String filename)
			throws FileNotFoundException, IOException {
		File headsFile = new File(getDirectory(), filename);
		if (heads != null) {
			BufferedOutputStream bos = new SafeBufferedOutputStream(
					new FileOutputStream(headsFile));
			try {
				for (ObjectId id : heads) {
					id.copyTo(bos);
					bos.write('\n');
				}
			} finally {
				bos.close();
			}
		} else {
			FileUtils.delete(headsFile, FileUtils.SKIP_MISSING);
		}
	}
