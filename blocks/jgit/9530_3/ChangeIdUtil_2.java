
	public static int findFirstFooterLine(String[] lines) {
		int footerFirstLine = lines.length;
		for (int i = lines.length - 1; i > 1; --i) {
			if (footerPattern.matcher(lines[i]).matches()) {
				footerFirstLine = i;
				continue;
			}
			if (footerFirstLine != lines.length && lines[i].length() == 0) {
				break;
			}
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
