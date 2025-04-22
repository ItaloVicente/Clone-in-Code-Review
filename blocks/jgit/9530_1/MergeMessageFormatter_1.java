		StringBuilder sb = new StringBuilder();
		boolean hasFooter = false;
		for (int i = 0; i < lines.length; i++) {
			if (footerPattern.matcher(lines[i]).matches()) {
				hasFooter = true;
				addConflictsMessage(conflictingPaths
			}
		}
		if (!hasFooter) {
			addConflictsMessage(conflictingPaths
		}
		return sb.toString();
	}

	private void addConflictsMessage(List<String> conflictingPaths
			StringBuilder sb) {
