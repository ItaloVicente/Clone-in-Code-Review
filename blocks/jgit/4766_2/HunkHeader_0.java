
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("HunkHeader[");
		buf.append(getOldImage().getStartLine());
		buf.append('
		buf.append(getOldImage().getLineCount());
		buf.append("->");
		buf.append(getNewStartLine()).append('
		buf.append(']');
		return buf.toString();
	}
