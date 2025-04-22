
	public static void copyFile(File sourceFile
			throws IOException {
		if (destFile.exists()) {
			if (destFile.isDirectory()) {
				throw new IOException(MessageFormat.format(
						JGitText.get().copyFailureDestinationIsDirectory
						destFile));
			} else if (!destFile.canWrite()) {
				throw new IOException(MessageFormat.format(
						JGitText.get().copyFailureDestinationIsReadOnly
						destFile));
			}
		}
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(destFile);

			byte[] buffer = new byte[FILE_COPY_BUFFER_SIZE];
			int read = inputStream.read(buffer);
			while (read != -1) {
				outputStream.write(buffer
				read = inputStream.read(buffer);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
