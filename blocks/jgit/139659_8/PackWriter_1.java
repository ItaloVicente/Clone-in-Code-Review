	private class DepthAwareVisitationPolicy
			implements ObjectWalk.VisitationPolicy {
		private final Map<ObjectId

		private final ObjectWalk walk;

		DepthAwareVisitationPolicy(ObjectWalk walk) {
			this.walk = requireNonNull(walk);
		}

