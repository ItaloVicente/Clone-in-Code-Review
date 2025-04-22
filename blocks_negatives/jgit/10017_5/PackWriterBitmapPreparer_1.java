		if (cardinality <= shift || idxFromStart <= shift)
			return minCommits;

		int shiftedCardinality = cardinality - shift;
		long shiftedIdxFromStart = idxFromStart - shift;
		long numerator = shiftedIdxFromStart * (maxCommits - minCommits);
		int minDesired = (int) (numerator / shiftedCardinality + minCommits);
		int minAllowed = Math.max(shiftedCardinality / 2, minCommits);
		return Math.min(minDesired, minAllowed);
