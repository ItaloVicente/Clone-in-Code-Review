	final Algorithm patience = new Algorithm() {
		DiffAlgorithm create() {
			PatienceDiff d = new PatienceDiff();
			d.setFallbackAlgorithm(null);
			return d;
		}
	};

	final Algorithm patience_myers = new Algorithm() {
		DiffAlgorithm create() {
			PatienceDiff d = new PatienceDiff();
			d.setFallbackAlgorithm(MyersDiff.INSTANCE);
			return d;
		}
	};

	final Algorithm patience_histogram_myers = new Algorithm() {
		DiffAlgorithm create() {
			HistogramDiff d2 = new HistogramDiff();
			d2.setFallbackAlgorithm(MyersDiff.INSTANCE);
			PatienceDiff d1 = new PatienceDiff();
			d1.setFallbackAlgorithm(d2);
			return d1;
		}
	};

