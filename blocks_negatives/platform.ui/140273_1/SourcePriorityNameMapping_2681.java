	 * Computes the source priority for the given expression. The source
	 * priority is a bit mask of all of the variables references by the
	 * expression. The default variable is considered to be
	 * {@link ISources#ACTIVE_CURRENT_SELECTION}. The source priority is used
	 * to minimize recomputations of the expression, and it can also be used for
	 * conflict resolution.
