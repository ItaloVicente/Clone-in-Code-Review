	private IPath adaptIPath() {
		switch (getType()) {
		case REPO:
		case WORKINGDIR:
		case FILE:
		case FOLDER:
			return getPath();
		default:
			return null;
		}
	}

