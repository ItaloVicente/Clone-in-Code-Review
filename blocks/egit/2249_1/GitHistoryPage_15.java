	private void createNewWalk(Repository db, AnyObjectId headId) {
		currentHeadId = headId;
		if (currentWalk != null)
			currentWalk.release();
		currentWalk = new SWTWalk(db);
		currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
		currentWalk.sort(RevSort.BOUNDARY, true);
		highlightFlag = currentWalk.newFlag("highlight"); //$NON-NLS-1$
