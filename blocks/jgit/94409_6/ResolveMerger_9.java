	private static MergeAlgorithm getMergeAlgorithm(Config config) {
		SupportedAlgorithm diffAlg = config.getEnum(
				CONFIG_DIFF_SECTION
				HISTOGRAM);
		return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
	}

	private static String[] defaultCommitNames() {
		return new String[] { "BASE"
	}

