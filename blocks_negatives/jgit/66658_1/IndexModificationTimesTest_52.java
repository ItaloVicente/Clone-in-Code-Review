		dc = db.readDirCache();
		entry = dc.getEntry(path);

		assertTrue("shall have equal mod time!", masterLastMod == sideLastMode);
		assertTrue("shall not equal master timestamp!",
				entry.getLastModified() == masterLastMod);
