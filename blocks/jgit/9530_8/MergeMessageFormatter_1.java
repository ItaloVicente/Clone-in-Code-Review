		StringBuilder sb = new StringBuilder();
		int firstFooterLine = ChangeIdUtil.indexOfFirstFooterLine(lines);
		for (int i = 0; i < firstFooterLine; i++)
			sb.append(lines[i]).append('\n');
		if (firstFooterLine == lines.length && message.length() != 0)
			sb.append('\n');
		addConflictsMessage(conflictingPaths
		if (firstFooterLine < lines.length)
			sb.append('\n');
		for (int i = firstFooterLine; i < lines.length; i++)
			sb.append(lines[i]).append('\n');
		return sb.toString();
	}

	private static void addConflictsMessage(List<String> conflictingPaths
			StringBuilder sb) {
