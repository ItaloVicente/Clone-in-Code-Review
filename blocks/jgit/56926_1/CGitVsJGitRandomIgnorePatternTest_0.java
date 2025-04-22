	private static class PseudoRandomPatternGenerator {

		private static final int DEFAULT_MAX_FRAGMENTS_PER_PATTERN = 15;

		private static final double DEFAULT_SPECIAL_FRAGMENTS_FREQUENCY = 0.75d;

		private static final List<String> SPECIAL_FRAGMENTS = Arrays.asList(
				"\\"
				")"
				":"

		);

		private static final String STANDARD_CHARACTERS = new String(
				"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");

		private final Random random = new Random();

		private int maxFragmentsPerPattern = DEFAULT_MAX_FRAGMENTS_PER_PATTERN;

		private double specialFragmentsFrequency = DEFAULT_SPECIAL_FRAGMENTS_FREQUENCY;

		public PseudoRandomPatternGenerator() {

		}

		public String nextRandomString() {
			StringBuilder builder = new StringBuilder();
			int length = randomFragmentCount();
			for (int i = 0; i < length; i++) {
				if (useSpecialFragment()) {
					builder.append(randomSpecialFragment());
				} else {
					builder.append(randomStandardCharacters());
				}

			}
			return builder.toString();
		}

		private int randomFragmentCount() {
			return 1 + random.nextInt(maxFragmentsPerPattern - 1);
		}

		private char randomStandardCharacters() {
			return STANDARD_CHARACTERS
					.charAt(random.nextInt(STANDARD_CHARACTERS.length()));
		}

		private boolean useSpecialFragment() {
			return random.nextDouble() > specialFragmentsFrequency;
		}

		private String randomSpecialFragment() {
			return SPECIAL_FRAGMENTS
					.get(random.nextInt(SPECIAL_FRAGMENTS.size()));
		}
	}

