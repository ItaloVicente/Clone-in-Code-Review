		/**
		 * Create the following commits and then attempt to rebase topic onto
		 * master. This will fail as the cherry-pick list C, D, E an F contains
		 * a merge commit (F).
		 *
		 * <pre>
		 * A - B (master)
		 *   \
		 *    C - D - F (topic)
		 *     \      /
		 *      E  -  (side)
		 * </pre>
		 */
