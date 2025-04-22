
		public void close() {
			oldSource.close();
			newSource.close();
		}

		public boolean isWorkingTreeSource(DiffEntry.Side side) {
			switch (side) {
			case OLD:
				return oldSource.isWorkingTreeSource();
			case NEW:
				return newSource.isWorkingTreeSource();
			default:
				throw new IllegalArgumentException();
			}
		}

