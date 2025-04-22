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
			throw new IOException(CoreText.BranchOperation_couldNotDelete + file.getPath());
		}
	}

	private void mapObjects() {
		oldTree = oldCommit.getTree();
		newTree = newCommit.getTree();
	}

