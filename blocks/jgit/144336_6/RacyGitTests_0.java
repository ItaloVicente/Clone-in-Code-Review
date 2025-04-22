
		FileTreeIterator f = new FileTreeIterator(db.getWorkTree()
				db.getConfig().get(WorkingTreeOptions.KEY));
		assertTrue(f.findFile("a"));
		try (ObjectReader reader = db.newObjectReader()) {
			assertFalse(f.isModified(dc.getEntry("a")
		}
