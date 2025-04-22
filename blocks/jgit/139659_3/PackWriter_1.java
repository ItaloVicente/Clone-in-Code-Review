	private class DepthAwareSeenPolicy implements ObjectWalk.SeenPolicy {
		private final Map<ObjectId

		private final ObjectWalk walk;

		DepthAwareSeenPolicy(ObjectWalk walk) {
			this.walk = requireNonNull(walk);
		}

