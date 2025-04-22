	public static String hardWrap(String text) {
		int[] wrapOffsets = calculateWrapOffsets(text, MAX_LINE_WIDTH);
		if (wrapOffsets != null) {
			StringBuilder builder = new StringBuilder(text.length() + wrapOffsets.length);
			int prev = 0;
			for (int cur : wrapOffsets) {
				builder.append(text.substring(prev, cur));
				for (int j = cur; j > prev && builder.charAt(builder.length() - 1) == ' '; j--) {
					builder.deleteCharAt(builder.length() - 1);
				}
				builder.append('\n');
				prev = cur;
			}
			builder.append(text.substring(prev));
			return builder.toString();
		}
		return text;
