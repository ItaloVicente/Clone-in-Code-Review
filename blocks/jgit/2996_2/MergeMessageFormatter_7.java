	public String formatWithConflicts(String message
			List<String> conflictingPaths) {
		StringBuilder sb = new StringBuilder(message);
		if (!message.endsWith("\n"))
			sb.append("\n");
		sb.append("\n");
		sb.append("Conflicts:\n");
		for (String conflictingPath : conflictingPaths)
			sb.append('\t').append(conflictingPath).append('\n');
		return sb.toString();
	}

