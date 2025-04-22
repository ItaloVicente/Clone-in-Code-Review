	@Deprecated
	public static class Statistics {
		public static class ObjectType {
			private PackStatistics.ObjectType objectType;

			public ObjectType(PackStatistics.ObjectType type) {
				objectType = type;
			}

			public long getObjects() {
				return objectType.getObjects();
			}

			public long getDeltas() {
				return objectType.getDeltas();
			}

			public long getReusedObjects() {
				return objectType.getReusedObjects();
			}

			public long getReusedDeltas() {
				return objectType.getReusedDeltas();
			}

			public long getBytes() {
				return objectType.getBytes();
			}

			public long getDeltaBytes() {
				return objectType.getDeltaBytes();
			}
		}

		private PackStatistics statistics;

		public Statistics(PackStatistics stats) {
			statistics = stats;
		}

		public Set<ObjectId> getInterestingObjects() {
			return statistics.getInterestingObjects();
		}

		public Set<ObjectId> getUninterestingObjects() {
			return statistics.getUninterestingObjects();
		}

		public Collection<CachedPack> getReusedPacks() {
			return statistics.getReusedPacks();
		}

		public int getDeltaSearchNonEdgeObjects() {
			return statistics.getDeltaSearchNonEdgeObjects();
		}

		public int getDeltasFound() {
			return statistics.getDeltasFound();
		}

		public long getTotalObjects() {
			return statistics.getTotalObjects();
		}

		public long getBitmapIndexMisses() {
			return statistics.getBitmapIndexMisses();
		}

		public long getTotalDeltas() {
			return statistics.getTotalDeltas();
		}

		public long getReusedObjects() {
			return statistics.getReusedObjects();
		}

		public long getReusedDeltas() {
			return statistics.getReusedDeltas();
		}

		public long getTotalBytes() {
			return statistics.getTotalBytes();
		}

		public long getThinPackBytes() {
			return statistics.getThinPackBytes();
		}

		public ObjectType byObjectType(int typeCode) {
			return new ObjectType(statistics.byObjectType(typeCode));
		}

		public boolean isShallow() {
			return statistics.isShallow();
		}

		public int getDepth() {
			return statistics.getDepth();
		}

		public long getTimeCounting() {
			return statistics.getTimeCounting();
		}

		public long getTimeSearchingForReuse() {
			return statistics.getTimeSearchingForReuse();
		}

		public long getTimeSearchingForSizes() {
			return statistics.getTimeSearchingForSizes();
		}

		public long getTimeCompressing() {
			return statistics.getTimeCompressing();
		}

		public long getTimeWriting() {
			return statistics.getTimeWriting();
		}

		public long getTimeTotal() {
			return statistics.getTimeTotal();
		}

		public double getTransferRate() {
			return statistics.getTransferRate();
		}

		public String getMessage() {
			return statistics.getMessage();
		}
	}

