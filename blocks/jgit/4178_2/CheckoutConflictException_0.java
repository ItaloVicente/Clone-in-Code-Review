	private static String buildList(String[] files) {
		StringBuilder builder = new StringBuilder();
		for (String f : files) {
			builder.append("\n");
			builder.append(f);
		}
		return builder.toString();
