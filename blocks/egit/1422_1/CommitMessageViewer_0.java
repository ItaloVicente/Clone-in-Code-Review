			return super.equals(object) && targetCommit.equals(((ObjectLink)object).targetCommit);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ targetCommit.hashCode();
