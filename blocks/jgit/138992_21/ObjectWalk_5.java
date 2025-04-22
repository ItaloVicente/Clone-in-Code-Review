	public interface SeenPolicy {
		boolean checkSeen(RevObject o);

		void setSeen(RevObject o);
	}

	public static final SeenPolicy SIMPLE_SEEN_POLICY = new SeenPolicy() {
		@Override
		public boolean checkSeen(RevObject o) {
			return (o.flags & SEEN) != 0;
		}

		@Override
		public void setSeen(RevObject o) {
			o.flags |= SEEN;
		}
	};

