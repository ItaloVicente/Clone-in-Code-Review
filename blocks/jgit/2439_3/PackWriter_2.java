		long totalBytes;

		long thinPackBytes;

		long timeCounting;

		long timeCompressing;

		long timeWriting;

		public Set<ObjectId> getInterestingObjects() {
			return interestingObjects;
		}

		public Set<ObjectId> getUninterestingObjects() {
			return uninterestingObjects;
		}

		public Collection<CachedPack> getReusedPacks() {
			return reusedPacks;
		}

		public int getDeltaSearchNonEdgeObjects() {
			return deltaSearchNonEdgeObjects;
		}

		public int getDeltasFound() {
			return deltasFound;
		}

		public long getTotalObjects() {
			return totalObjects;
		}

		public long getTotalDeltas() {
			return totalDeltas;
		}

		public long getReusedObjects() {
			return reusedObjects;
		}

		public long getReusedDeltas() {
			return reusedDeltas;
		}

		public long getTotalBytes() {
			return totalBytes;
		}

		public long getThinPackBytes() {
			return thinPackBytes;
		}

		public long getTimeCounting() {
			return timeCounting;
		}

		public long getTimeCompressing() {
			return timeCompressing;
		}

		public long getTimeWriting() {
			return timeWriting;
		}

		public double getTransferRate() {
			return getTotalBytes() / (getTimeWriting() / 1000.0);
		}

