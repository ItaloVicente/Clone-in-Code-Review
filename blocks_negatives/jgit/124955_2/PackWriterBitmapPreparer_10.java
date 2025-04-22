	 * walks all of the reachable commits via the branch tips (
	 * {@code peeledWants}), stores them in {@code commitsByOldest}, and sets up
	 * bitmaps for each branch tip ({@code tipCommitBitmaps}).
	 * {@code commitsByOldest} is initialized with an expected size of all
	 * commits, but may be smaller if some commits are unreachable, in which
	 * case {@code commitStartPos} will contain a positive offset to the root
	 * commit.
