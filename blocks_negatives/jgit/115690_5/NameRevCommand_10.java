	private static class NameRevCommit extends RevCommit {
		private String tip;
		private int distance;
		private long cost;

		private NameRevCommit(AnyObjectId id) {
			super(id);
		}

		private StringBuilder format() {
			StringBuilder sb = new StringBuilder(tip);
			if (distance > 0)
				sb.append('~').append(distance);
			return sb;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(getClass().getSimpleName())
				.append('[');
			if (tip != null)
				sb.append(format());
			else
				sb.append((Object) null);
			sb.append(',').append(cost).append(']').append(' ')
				.append(super.toString()).toString();
			return sb.toString();
		}
	}

