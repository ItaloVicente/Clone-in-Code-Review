	private static int getAdjustedWeight(ObjectToPack o) {
		if (o.isEdge() || o.doNotAttemptDelta()) {
			return 0;
		}
		return o.getWeight();
	}

