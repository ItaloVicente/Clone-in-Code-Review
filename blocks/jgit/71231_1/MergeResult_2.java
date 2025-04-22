
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		String prefix = "";
		StringBuffer b = new StringBuffer();
		b.append("{#sequences:").append(sequences.size());
		b.append("
		for (MergeChunk c : this) {
			b.append(prefix).append(c);
			prefix = "
		}
		b.append("]}");
		return (b.toString());
	}
