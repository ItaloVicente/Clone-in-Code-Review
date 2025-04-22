		for (Iterator<StagingEntry> it = iss.iterator(); it.hasNext();) {
			String relativePath = it.next().getPath();
			String path = new Path(currentRepository.getWorkTree()
					.getAbsolutePath()).append(relativePath)
					.toOSString();
			openFileInEditor(path);
