
	public static int indexOfChangeId(String message
		String[] lines = message.split(delimiter);
		int footerFirstLine = indexOfFirstFooterLine(lines);
		if (footerFirstLine == lines.length)
			return -1;

		int indexOfFooter = 0;
		for (int i = 0; i < footerFirstLine; ++i)
			indexOfFooter += lines[i].length() + delimiter.length();
		return message.indexOf(CHANGE_ID
	}

	public static int indexOfFirstFooterLine(String[] lines) {
		int footerFirstLine = lines.length;
		for (int i = lines.length - 1; i > 1; --i) {
			if (footerPattern.matcher(lines[i]).matches()) {
				footerFirstLine = i;
				continue;
			}
			if (footerFirstLine != lines.length && lines[i].length() == 0)
				break;
			if (footerFirstLine != lines.length
					&& includeInFooterPattern.matcher(lines[i]).matches()) {
				footerFirstLine = i + 1;
				continue;
			}
			footerFirstLine = lines.length;
			break;
		}
		return footerFirstLine;
	}
