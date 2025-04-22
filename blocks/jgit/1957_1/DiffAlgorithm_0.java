	public enum SupportedAlgorithm {
		myers(MyersDiff.INSTANCE)

		histogram(new HistogramDiff());

		public DiffAlgorithm algorithm;

		SupportedAlgorithm(DiffAlgorithm a) {
			algorithm = a;
		}
	};

