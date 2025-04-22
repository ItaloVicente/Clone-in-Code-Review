
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("HunkHeader[");
		buf.append(getOldImage().getStartLine() + "
		buf.append(getNewStartLine() + "
		return buf.toString();
	}
