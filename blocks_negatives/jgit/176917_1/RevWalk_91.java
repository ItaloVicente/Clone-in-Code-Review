	public ReachabilityChecker createReachabilityChecker() throws IOException {
		if (reader.getBitmapIndex() != null) {
			return new BitmappedReachabilityChecker(this);
		}

		return new PedestrianReachabilityChecker(true, this);
