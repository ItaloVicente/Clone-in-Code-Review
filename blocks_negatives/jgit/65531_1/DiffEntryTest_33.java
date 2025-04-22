		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c1 = git.commit().setMessage("initial commit").call();
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		final TreeWalk walk = new TreeWalk(db);
		walk.addTree(c1.getTree());
		walk.setRecursive(true);
		assertTrue(walk.next());

		editor.add(new PathEdit("a.txt") {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.EXECUTABLE_FILE);
				ent.setObjectId(walk.getObjectId(0));
			}
		});
		assertTrue(editor.commit());
		RevCommit c2 = git.commit().setMessage("second commit").call();
		walk.reset();
		walk.addTree(c1.getTree());
		walk.addTree(c2.getTree());
		List<DiffEntry> diffs = DiffEntry.scan(walk, false);
		assertEquals(1, diffs.size());
		DiffEntry diff = diffs.get(0);
		assertEquals(ChangeType.MODIFY,diff.getChangeType());
		assertEquals(diff.getOldId(), diff.getNewId());
		assertEquals("a.txt", diff.getOldPath());
		assertEquals(diff.getOldPath(), diff.getNewPath());
		assertEquals(FileMode.EXECUTABLE_FILE, diff.getNewMode());
		assertEquals(FileMode.REGULAR_FILE, diff.getOldMode());
