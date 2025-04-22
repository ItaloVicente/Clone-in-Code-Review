	/** Source the chunk (what code path created it). */
	public static enum Source implements TinyProtobuf.Enum {
		/** Came in over the network from an external source */
		RECEIVE(1),
		/** Created in this repository (e.g. a merge). */
		INSERT(2),
		/** Generated during a repack of this repository. */
		REPACK(3);

		private final int value;

		Source(int val) {
			this.value = val;
		}

		public int value() {
			return value;
		}
	}

