		for (Object element : iss.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				String relativePath = entry.getPath();
				String path = new Path(currentRepository.getWorkTree()
						.getAbsolutePath()).append(relativePath)
						.toOSString();
				openFileInEditor(path);
			}
