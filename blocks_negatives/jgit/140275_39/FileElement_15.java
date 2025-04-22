	 * Return a temporary file within passed directory and fills it with stream
	 * if valid.
	 *
	 * @param directory
	 *            the directory where the temporary file is created
	 * @param midName
	 *            name added in the middle of generated temporary file name
	 * @return the object stream
	 * @throws IOException
	 */
	public File getFile(File directory, String midName) throws IOException {
		if ((tempFile != null) && (stream == null)) {
			return tempFile;
		}
		tempFile = getTempFile(path, directory, midName);
		return copyFromStream(tempFile, stream);
	}

	/**
	 * Return a real file from work tree or a temporary file with content if
	 * stream is valid or if path is "/dev/null"
