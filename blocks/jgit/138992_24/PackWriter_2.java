	private static final ObjectWalk.VisitationPolicy ALWAYS_VISIT_POLICY =
			new ObjectWalk.VisitationPolicy() {
		@Override
		public boolean shouldVisit(RevObject o) {
			return true;
		}

		@Override
		public void visited(RevObject o) {}
	};

