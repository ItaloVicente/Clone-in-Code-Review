
		final int smudge_s;
		final int smudge_ns;
		if (myLock != null) {
			myLock.createCommitSmapshot();
			snapshot = myLock.getCommitSnapshot();
			smudge_s = (int) (snapshot.lastModified() / 1000);
			smudge_ns = ((int) (snapshot.lastModified() % 1000)) * 1000000;
