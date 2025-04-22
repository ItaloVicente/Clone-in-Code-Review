		@Override
		public boolean equals(Object o) {
			return super.equals(o) && color.equals(((SwingLane)o).color);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ color.hashCode();
		}
