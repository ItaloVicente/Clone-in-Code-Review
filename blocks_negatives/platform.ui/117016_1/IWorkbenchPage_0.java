	 * This working set is never <code>null</code>, even if there are no
	 * working sets assigned to this page via
	 * {@link #setWorkingSets(IWorkingSet[])}. It is recommended that any
	 * client that uses this API be aware of this and act accordingly.
	 * Specifically, it is recommended that any client utilizing this or any
	 * other IWorkingSet whose {@link IWorkingSet#isAggregateWorkingSet()}
	 * returns <code>true</code> act as if they are not using any working set
	 * if the set is empty. These clients should also maintain an awareness of
	 * the contents of aggregate working sets and toggle this behavior should
	 * the contents of the aggregate either become empty or non-empty.
