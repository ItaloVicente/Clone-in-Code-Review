
		@Override
		public long getApproximateObjectCount() {
			long count = 0;
			for (DfsPackDescription p : packs) {
				count += p.getObjectCount();
			}
			return count;
		}
