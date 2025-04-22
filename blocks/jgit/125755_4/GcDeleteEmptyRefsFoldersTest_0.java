		assertFalse(refDir02.getParent().toFile().exists());
		assertFalse(refDir02.getParent().getParent().toFile().exists());
	}

	private void setLastModifiedTime(FileTime fileTime
		long numParents = folder.chars().filter(c -> c == '/').count();
		Path folderPath = path.resolve(folder);
		for(int folderLevel = 0; folderLevel <= numParents; folderLevel ++ ) {
			Files.setLastModifiedTime(folderPath
			folderPath = folderPath.getParent();
		}
