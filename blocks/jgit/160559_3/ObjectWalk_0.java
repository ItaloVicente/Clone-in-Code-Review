	public ObjectReachabilityChecker createObjectReachabilityChecker()
			throws IOException {
		if (reader.getBitmapIndex() != null) {
			return new BitmappedObjectReachabilityChecker(this);
		}

		return new PedestrianObjectReachabilityChecker(this);
	}

