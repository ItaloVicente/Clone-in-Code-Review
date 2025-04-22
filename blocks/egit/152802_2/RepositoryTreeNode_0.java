	private File adaptFile() {
		switch (getType()) {
		case REPO:
		case WORKINGDIR:
		case FILE:
		case FOLDER:
			IPath path = getPath();
			return path != null ? path.toFile() : null;
		default:
			return null;
		}
	}

