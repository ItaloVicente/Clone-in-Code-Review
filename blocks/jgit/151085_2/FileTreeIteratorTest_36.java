		try (TreeWalk tw = new TreeWalk(db)) {
			FileTreeIterator fti = new FileTreeIterator(trash
					db.getConfig().get(WorkingTreeOptions.KEY));
			tw.addTree(fti);
			DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
			tw.addTree(dci);
			fti.setDirCacheIterator(tw
			while (tw.next() && !tw.getPathString().equals("file")) {
			}
			assertEquals(MetadataDiff.EQUAL
			ObjectId fromRaw = ObjectId.fromRaw(fti.idBuffer()
			assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
					fromRaw.getName());
			try (ObjectReader objectReader = db.newObjectReader()) {
				assertFalse(fti.isModified(dce
			}
