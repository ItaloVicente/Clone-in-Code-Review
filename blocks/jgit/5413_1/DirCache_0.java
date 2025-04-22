
		myLock.createCommitSmapshot();
		snapshot = myLock.getCommitSnapshot();
		final int smudge_s = (int) (snapshot.lastModified() / 1000);
		final int smudge_ns = ((int) (snapshot.lastModified() % 1000)) * 1000000;
		for (int i = 0; i < entryCnt; i++) {
			final DirCacheEntry e = sortedEntries[i];
			if (e.mightBeRacilyClean(smudge_s
				e.smudgeRacilyClean();
			e.write(dos);
