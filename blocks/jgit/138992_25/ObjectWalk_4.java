	public interface VisitationPolicy {
		boolean shouldVisit(RevObject o);

		void visited(RevObject o);
	}

	public static final VisitationPolicy SIMPLE_VISITATION_POLICY =
			new VisitationPolicy() {
		@Override
		public boolean shouldVisit(RevObject o) {
			return (o.flags & SEEN) == 0;
		}

		@Override
		public void visited(RevObject o) {
			o.flags |= SEEN;
		}
	};

