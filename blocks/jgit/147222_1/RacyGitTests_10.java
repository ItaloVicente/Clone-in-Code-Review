		fsTick(db.getIndexFile());

		File updatedA = writeToWorkDir("a"
		Instant newLastModified = TimeUtil
				.setLastModifiedWithOffset(updatedA.toPath()
		resetIndex(new FileTreeIterator(db));
		FS.DETECTED.setLastModified(db.getIndexFile().toPath()
				newLastModified);

		DirCache dc = db.readDirCache();
