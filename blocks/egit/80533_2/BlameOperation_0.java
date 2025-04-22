
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof BlameHistoryPageInput)) {
				return false;
			}
			BlameHistoryPageInput other = (BlameHistoryPageInput) obj;
			return super.equals(obj)
					&& (commit == other.commit
							|| commit != null && commit.equals(other.commit));
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ (commit == null ? 0 : commit.hashCode());
		}
