		public long getTimeTotal() {
			return timeCounting
				+ timeSearchingForReuse
				+ timeSearchingForSizes
				+ timeCompressing
				+ timeWriting;
		}

