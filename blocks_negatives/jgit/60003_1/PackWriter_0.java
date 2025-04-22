	/**
	 * Summary of how PackWriter created the pack.
	 *
	 * @deprecated Use {@link PackStatistics} instead.
	 */
	@Deprecated
	public static class Statistics {
		/** Statistics about a single class of object. */
		public static class ObjectType {
			private PackStatistics.ObjectType objectType;

			/**
			 * Wraps an
			 * {@link org.eclipse.jgit.storage.pack.PackStatistics.ObjectType}
			 * instance to maintain backwards compatibility with existing API.
			 *
			 * @param type
			 *            the wrapped instance
			 */
			public ObjectType(PackStatistics.ObjectType type) {
				objectType = type;
			}

			/**
			 * @return total number of objects output. This total includes the
			 *         value of {@link #getDeltas()}.
			 */
			public long getObjects() {
				return objectType.getObjects();
			}

			/**
			 * @return total number of deltas output. This may be lower than the
			 *         actual number of deltas if a cached pack was reused.
			 */
			public long getDeltas() {
				return objectType.getDeltas();
			}

			/**
			 * @return number of objects whose existing representation was
			 *         reused in the output. This count includes
			 *         {@link #getReusedDeltas()}.
			 */
			public long getReusedObjects() {
				return objectType.getReusedObjects();
			}

			/**
			 * @return number of deltas whose existing representation was reused
			 *         in the output, as their base object was also output or
			 *         was assumed present for a thin pack. This may be lower
			 *         than the actual number of reused deltas if a cached pack
			 *         was reused.
			 */
			public long getReusedDeltas() {
				return objectType.getReusedDeltas();
			}

			/**
			 * @return total number of bytes written. This size includes the
			 *         object headers as well as the compressed data. This size
			 *         also includes all of {@link #getDeltaBytes()}.
			 */
			public long getBytes() {
				return objectType.getBytes();
			}

			/**
			 * @return number of delta bytes written. This size includes the
			 *         object headers for the delta objects.
			 */
			public long getDeltaBytes() {
				return objectType.getDeltaBytes();
			}
		}

		private PackStatistics statistics;

		/**
		 * Wraps a {@link PackStatistics} object to maintain backwards
		 * compatibility with existing API.
		 *
		 * @param stats
		 *            the wrapped PackStatitics object
		 */
		public Statistics(PackStatistics stats) {
			statistics = stats;
		}

		/**
		 * @return unmodifiable collection of objects to be included in the
		 *         pack. May be null if the pack was hand-crafted in a unit
		 *         test.
		 */
		public Set<ObjectId> getInterestingObjects() {
			return statistics.getInterestingObjects();
		}

		/**
		 * @return unmodifiable collection of objects that should be excluded
		 *         from the pack, as the peer that will receive the pack already
		 *         has these objects.
		 */
		public Set<ObjectId> getUninterestingObjects() {
			return statistics.getUninterestingObjects();
		}

		/**
		 * @return unmodifiable collection of the cached packs that were reused
		 *         in the output, if any were selected for reuse.
		 */
		public Collection<CachedPack> getReusedPacks() {
			return statistics.getReusedPacks();
		}

		/**
		 * @return number of objects in the output pack that went through the
		 *         delta search process in order to find a potential delta base.
		 */
		public int getDeltaSearchNonEdgeObjects() {
			return statistics.getDeltaSearchNonEdgeObjects();
		}

		/**
		 * @return number of objects in the output pack that went through delta
		 *         base search and found a suitable base. This is a subset of
		 *         {@link #getDeltaSearchNonEdgeObjects()}.
		 */
		public int getDeltasFound() {
			return statistics.getDeltasFound();
		}

		/**
		 * @return total number of objects output. This total includes the value
		 *         of {@link #getTotalDeltas()}.
		 */
		public long getTotalObjects() {
			return statistics.getTotalObjects();
		}

		/**
		 * @return the count of objects that needed to be discovered through an
		 *         object walk because they were not found in bitmap indices.
		 *         Returns -1 if no bitmap indices were found.
		 *
		 * @since 4.0
		 */
		public long getBitmapIndexMisses() {
			return statistics.getBitmapIndexMisses();
		}

		/**
		 * @return total number of deltas output. This may be lower than the
		 *         actual number of deltas if a cached pack was reused.
		 */
		public long getTotalDeltas() {
			return statistics.getTotalDeltas();
		}

		/**
		 * @return number of objects whose existing representation was reused in
		 *         the output. This count includes {@link #getReusedDeltas()}.
		 */
		public long getReusedObjects() {
			return statistics.getReusedObjects();
		}

		/**
		 * @return number of deltas whose existing representation was reused in
		 *         the output, as their base object was also output or was
		 *         assumed present for a thin pack. This may be lower than the
		 *         actual number of reused deltas if a cached pack was reused.
		 */
		public long getReusedDeltas() {
			return statistics.getReusedDeltas();
		}

		/**
		 * @return total number of bytes written. This size includes the pack
		 *         header, trailer, thin pack, and reused cached pack(s).
		 */
		public long getTotalBytes() {
			return statistics.getTotalBytes();
		}

		/**
		 * @return size of the thin pack in bytes, if a thin pack was generated.
		 *         A thin pack is created when the client already has objects
		 *         and some deltas are created against those objects, or if a
		 *         cached pack is being used and some deltas will reference
		 *         objects in the cached pack. This size does not include the
		 *         pack header or trailer.
		 */
		public long getThinPackBytes() {
			return statistics.getThinPackBytes();
		}

		/**
		 * @param typeCode
		 *            object type code, e.g. OBJ_COMMIT or OBJ_TREE.
		 * @return information about this type of object in the pack.
		 */
		public ObjectType byObjectType(int typeCode) {
			return new ObjectType(statistics.byObjectType(typeCode));
		}

		/** @return true if the resulting pack file was a shallow pack. */
		public boolean isShallow() {
			return statistics.isShallow();
		}

		/** @return depth (in commits) the pack includes if shallow. */
		public int getDepth() {
			return statistics.getDepth();
		}

		/**
		 * @return time in milliseconds spent enumerating the objects that need
		 *         to be included in the output. This time includes any restarts
		 *         that occur when a cached pack is selected for reuse.
		 */
		public long getTimeCounting() {
			return statistics.getTimeCounting();
		}

		/**
		 * @return time in milliseconds spent matching existing representations
		 *         against objects that will be transmitted, or that the client
		 *         can be assumed to already have.
		 */
		public long getTimeSearchingForReuse() {
			return statistics.getTimeSearchingForReuse();
		}

		/**
		 * @return time in milliseconds spent finding the sizes of all objects
		 *         that will enter the delta compression search window. The
		 *         sizes need to be known to better match similar objects
		 *         together and improve delta compression ratios.
		 */
		public long getTimeSearchingForSizes() {
			return statistics.getTimeSearchingForSizes();
		}

		/**
		 * @return time in milliseconds spent on delta compression. This is
		 *         observed wall-clock time and does not accurately track CPU
		 *         time used when multiple threads were used to perform the
		 *         delta compression.
		 */
		public long getTimeCompressing() {
			return statistics.getTimeCompressing();
		}

		/**
		 * @return time in milliseconds spent writing the pack output, from
		 *         start of header until end of trailer. The transfer speed can
		 *         be approximated by dividing {@link #getTotalBytes()} by this
		 *         value.
		 */
		public long getTimeWriting() {
			return statistics.getTimeWriting();
		}

		/** @return total time spent processing this pack. */
		public long getTimeTotal() {
			return statistics.getTimeTotal();
		}

		/**
		 * @return get the average output speed in terms of bytes-per-second.
		 *         {@code getTotalBytes() / (getTimeWriting() / 1000.0)}.
		 */
		public double getTransferRate() {
			return statistics.getTransferRate();
		}

		/** @return formatted message string for display to clients. */
		public String getMessage() {
			return statistics.getMessage();
		}
	}

