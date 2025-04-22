	private void dropFromWindow(@SuppressWarnings("unused") DeltaWindowEntry src) {
	}

	private boolean isBetterDelta(DeltaWindowEntry src,
			TemporaryBuffer.Heap resDelta) {
		if (bestDelta == null)
			return true;

		if (resDelta.length() == bestDelta.length())
			return src.depth() < bestBase.depth();

		return resDelta.length() < bestDelta.length();
	}

	private static int deltaSizeLimit(DeltaWindowEntry res, int maxDepth,
			DeltaWindowEntry src) {
		final int limit = res.size() / 2 - 20;

		final int remainingDepth = maxDepth - src.depth();
		return (limit * remainingDepth) / maxDepth;
	}

