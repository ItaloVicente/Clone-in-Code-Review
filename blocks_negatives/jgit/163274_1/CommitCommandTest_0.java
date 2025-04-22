		DirCacheEntry stage1 = new DirCacheEntry(file, DirCacheEntry.STAGE_1);
		DirCacheEntry stage2 = new DirCacheEntry(file, DirCacheEntry.STAGE_2);
		DirCacheEntry stage3 = new DirCacheEntry(file, DirCacheEntry.STAGE_3);
		stage1.setFileMode(FileMode.REGULAR_FILE);
		stage2.setFileMode(FileMode.REGULAR_FILE);
		stage3.setFileMode(FileMode.REGULAR_FILE);
