
		boolean in(AlternateHandle[] alts) {
			for (AlternateHandle a : alts) {
				if (this.equals(a)) return true;
			}
			return false;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof AlternateHandle
				&& this.db.equals(((AlternateHandle) o).db);
		}

		@Override
		public int hashCode() {
			return 17 + (3 * this.db.getDirectory().hashCode());
		}
