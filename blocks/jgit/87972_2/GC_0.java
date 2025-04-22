	private static boolean isHead(Ref ref) {
		return ref.getName().startsWith(Constants.R_HEADS);
	}

	private static boolean isTag(Ref ref) {
		return ref.getName().startsWith(Constants.R_TAGS);
	}

