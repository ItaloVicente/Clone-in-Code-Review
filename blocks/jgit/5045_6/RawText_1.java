
	public String getLineDelimiter() {
		if (size() == 0)
			return null;
		int e = getEnd(0);
		if (content[e - 1] != '\n')
			return null;
		if (content.length > 1 && content[e - 2] == '\r')
			return "\r\n";
		else
			return "\n";
	}
