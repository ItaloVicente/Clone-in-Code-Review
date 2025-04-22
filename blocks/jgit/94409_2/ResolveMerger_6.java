	private static MergeAlgorithm getMergeAlgorithm(Config config) {
		SupportedAlgorithm diffAlg = config.getEnum(
				ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_ALGORITHM
				SupportedAlgorithm.HISTOGRAM);
		return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
	}

	private static String[] defaultCommitNames() {
		return new String[] { "BASE"
	}

