	enum SupportedAlgorithm {
		myers(MyersDiff.INSTANCE)

		public DiffAlgorithm algorithm;

		SupportedAlgorithm(DiffAlgorithm a) {
			algorithm = a;
		}
	};

	@Option(name = "--algorithm"
	void setAlgorithm(SupportedAlgorithm s) {
		diffFmt.setDiffAlgorithm(s.algorithm);
	}

