
		@Override
		public boolean equals(Object o) {
			return super.equals(o) && color.equals(((SWTLane)o).color);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ color.hashCode();
		}
