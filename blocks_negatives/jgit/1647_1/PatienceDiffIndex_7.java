	/**
	 * Scan all potential matches and find the longest common sequence.
	 *
	 * If this method returns non-null, the caller should copy out the
	 * {@link #nCommon} array and pass that through to the recursive sub-steps
	 * so that existing common matches can be reused rather than recomputed.
	 *
	 * @return an edit covering the longest common sequence. Null if there are
	 *         no common unique sequences present.
	 */
