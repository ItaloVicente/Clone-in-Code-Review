	protected static void deleteRecursive(File dirOrFile) throws IOException {
		if (dirOrFile.isDirectory()) {
			for (File file : dirOrFile.listFiles()) {
				deleteRecursive(file);
			}
		}
		boolean deleted = false;
		for(int i=0; i<10; i++) {
			deleted = dirOrFile.delete();
			if (deleted)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		if (!deleted) {
			throw new IOException("could not delete " + dirOrFile.getPath());
		}
	}

	protected static void deleteAllProjects() throws CoreException {
