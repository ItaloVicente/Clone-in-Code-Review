			long max = 0;
			for (DfsPackFile pack : packs) {
				max = Math.max(max
			}
			this.lastModified = max;
		}

		public long getLastModified() {
			if (lastModified < 0) {
				long max = 0;
				for (DfsPackFile pack : packs) {
					max = Math.max(max
				}
				lastModified = max;
			}
			return lastModified;
