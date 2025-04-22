	private static final ObjectWalk.SeenPolicy ALWAYS_VISIT_SEEN_POLICY =
			new ObjectWalk.SeenPolicy() {
		@Override
		public boolean checkSeen(RevObject o) {
			return false;
		}

		@Override
		public void setSeen(RevObject o) {}
	};

