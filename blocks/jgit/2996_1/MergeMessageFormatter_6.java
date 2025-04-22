	public String addConflictsSection(String message
			List<String> conflictingPaths) {
		StringBuilder sb = new StringBuilder(message);
		if (!message.endsWith("\n"))
			sb.append("\n");
		sb.append("\n");
		sb.append("Conflicts:");
		for (String conflictingPath : conflictingPaths) {
			sb.append("\n\t");
			sb.append(conflictingPath);
		}
		return sb.toString();
	}

