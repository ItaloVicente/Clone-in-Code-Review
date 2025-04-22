
	public String getEOL() {
		int e = getEnd(0);
		if (content.length > 1 && content[e - 2] == '\r'
				&& content[e - 1] == '\n')
			return "\r\n";
		if (content.length > 0 && content[e - 1] == '\n')
			return "\n";
		return null;
	}
