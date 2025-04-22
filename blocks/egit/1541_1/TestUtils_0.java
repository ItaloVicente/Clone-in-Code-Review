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

