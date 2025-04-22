	private File adaptIPath() {
		switch (getType()) {
		case REPO:
		case WORKINGDIR:
		case FILE:
		case FOLDER:
			return Optional.ofNullable(getPath()).map(IPath::toFile)
					.orElse(null);
		default:
			return null;
		}
	}

