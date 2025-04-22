		long indexMod = db.getIndexFile().lastModified();
		modTimes.add(Long.valueOf(indexMod));

		long aMod = addToWorkDir("a", "a2").lastModified();
		assumeTrue(aMod == indexMod);

		resetIndex(new FileTreeIteratorWithTimeControl(db, modTimes));

		db.readDirCache();
