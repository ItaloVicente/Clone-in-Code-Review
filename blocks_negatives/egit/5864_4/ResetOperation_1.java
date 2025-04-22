	/**
	 * Kind of reset
	 */
	public enum ResetType {
		/**
		 * Just change the ref. The index and workdir are not changed.
		 */
		SOFT,

		/**
		 * Change the ref and the index. The workdir is not changed.
		 */
		MIXED,

		/**
		 * Change the ref, the index and the workdir
		 */
		HARD
	}
