	/**
	 * Writes merged file content to the working tree. In case {@link #inCore}
	 * is set and we don't have a working tree the content is written to a
	 * temporary file
	 *
	 * @param result
	 *            the result of the content merge
	 * @return the file to which the merged content was written
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private File writeMergedFile(MergeResult<RawText> result)
			throws FileNotFoundException, IOException {
		MergeFormatter fmt = new MergeFormatter();
		File of = null;
		FileOutputStream fos;
		if (!inCore) {
			File workTree = db.getWorkTree();
			if (workTree == null)
				throw new UnsupportedOperationException();

			FS fs = db.getFS();
			of = new File(workTree, tw.getPathString());
			File parentFolder = of.getParentFile();
			if (!fs.exists(parentFolder))
				parentFolder.mkdirs();
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos, result, Arrays.asList(commitNames),
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
		} else if (!result.containsConflicts()) {
			of = File.createTempFile("merge_", "_temp", null); //$NON-NLS-1$ //$NON-NLS-2$
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos, result, Arrays.asList(commitNames),
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
