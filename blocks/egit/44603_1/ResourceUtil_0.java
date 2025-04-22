		IFile file = root.getFileForLocation(location);
		if (file == null) {
			return null;
		}
		if (isValid(file)) {
			return file;
		}
