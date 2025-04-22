
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("At ");
		buf.append(timestamp);
		if (traces.length != 0) {
			buf.append(" threads:\n");
			for (ThreadInfo threadInfo : traces) {
				buf.append(threadInfo.toString());
			}
		}
		return buf.toString();
	}
