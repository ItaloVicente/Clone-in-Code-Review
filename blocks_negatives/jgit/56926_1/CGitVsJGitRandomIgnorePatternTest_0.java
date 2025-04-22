	private static final int MAX_LENGTH = 15;

	private static String generateRandomPattern() {
		StringBuilder builder = new StringBuilder();
		int length = RANDOM.nextInt(MAX_LENGTH - 1) + 1;
		for (int i = 0; i < length; i++) {
			builder.append(PATTERN_FRAGMENTS
					.get(RANDOM.nextInt(PATTERN_FRAGMENTS.size())));
		}
		return builder.toString();
	}

	private static final List<String> PATTERN_FRAGMENTS = Arrays.asList("a",
			"b", "1", "2", "\\", "!", "#", "[", "]", "|", "/", "*", "?", "{",
			"}", "(", ")", "\\d", "(", "**", "[a\\]]", "\\ "

	);

	private static final Random RANDOM = new Random();

	private static String generateRandomPath() {
		return generateRandomPattern();
	}

