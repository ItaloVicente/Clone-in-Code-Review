	@NonNull
	public ReachabilityChecker createReachabilityChecker(RevWalk rw)
			throws IOException {
		if (rw.getObjectReader().getBitmapIndex() != null) {
			return new BitmappedReachabilityChecker(rw);
		}

		return new PedestrianReachabilityChecker(true
	}

	@NonNull
	public ObjectReachabilityChecker createObjectReachabilityChecker(
			ObjectWalk ow) throws IOException {
		if (ow.getObjectReader().getBitmapIndex() != null) {
			return new BitmappedObjectReachabilityChecker(ow);
		}

		return new PedestrianObjectReachabilityChecker(ow);
	}

