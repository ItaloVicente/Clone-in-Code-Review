
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(pattern);
		for (Attribute a : attributes) {
			sb.append(a);
		}
		return sb.toString();

	}
