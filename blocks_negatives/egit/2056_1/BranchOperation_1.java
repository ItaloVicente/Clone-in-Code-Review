			deleteFile(file);
		}
	}

	/**
	 * Deletes a file. Deletion is retried 10 times to avoid
	 * failing deletion caused by concurrent read.
	 * @param file
	 * @throws IOException
	 */
	private void deleteFile(File file) throws IOException {
		boolean deleted = false;
		for(int i=0; i<10; i++) {
			deleted = file.delete();
			if (deleted)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		if (!deleted) {
			throw new IOException(NLS.bind(
					CoreText.BranchOperation_couldNotDelete, file.getPath()));
