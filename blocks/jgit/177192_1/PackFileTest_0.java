		PackFile pfFromDirAndName = new PackFile(TEST_PACK_DIR
		assertPackFilesEqual(pf

		PackFile pfFromOIdAndExt = new PackFile(TEST_PACK_DIR
				PackExt.PACK);
		assertPackFilesEqual(pf

		PackFile pfFromIdAndExt = new PackFile(TEST_PACK_DIR
				PackExt.PACK);
		assertPackFilesEqual(pf
