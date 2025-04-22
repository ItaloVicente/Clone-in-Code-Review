	/**
	 * This method deletes a file / subtree
	 *
	 * @param d
	 *            file / folder to delete
	 * @throws IOException
	 *             if file can not be deleted
	 */
	public void deleteRecursive(File d) throws IOException {
		if (!d.exists())
			return;

		File[] files = d.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; ++i) {
				if (files[i].isDirectory())
					deleteRecursive(files[i]);
				else
					deleteFile(files[i]);
			}
		}
		deleteFile(d);
		assert !d.exists();
	}

	private void deleteFile(File file) throws IOException{
		boolean deleted = false;
		for (int i = 0; i < 10; i++) {
			if (file.delete()) {
				deleted = true;
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			System.out.println(">>> retried deleting " + file.getAbsolutePath());
		}
		if (!deleted)
			throw new IOException("Retried 10 times. Could not delete " + file.getAbsolutePath());
	}

