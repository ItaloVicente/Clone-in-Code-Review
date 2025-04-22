	 * Regression test for Bug 3840 [Viewers] free expansion of jar happening when deleting projects (1GEV2FL)
	 * Problem was:
	 *   - node has children A and B
	 *   - A is expanded, B is not
	 *   - A gets deleted
	 *   - B gets expanded because it reused A's item
