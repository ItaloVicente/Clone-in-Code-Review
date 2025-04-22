	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		for (final CommitItem item : items) {
			if (item.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result < item.getProblemSeverity()) {
					result = item.getProblemSeverity();
				}
			}
		}
		return result;
	}

