	public enum SupportedAlgorithm {
		MYERS

		HISTOGRAM
	}

	public static DiffAlgorithm getAlgorithm(SupportedAlgorithm alg) {
		switch (alg) {
		case MYERS:
			return MyersDiff.INSTANCE;
		case HISTOGRAM:
			return new HistogramDiff();
		default:
			throw new IllegalArgumentException();
		}
	}

