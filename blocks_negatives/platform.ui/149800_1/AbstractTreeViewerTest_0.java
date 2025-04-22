	 * Regression test for 1GBDB5A: ITPUI:WINNT - Exception in AbstractTreeViewer update.
	 * Problem was:
	 *   node has child A
	 *   node gets duplicate child A
	 *   viewer is refreshed rather than using add for new A
	 *   AbstractTreeViewer.updateChildren(...) was not properly handling it
