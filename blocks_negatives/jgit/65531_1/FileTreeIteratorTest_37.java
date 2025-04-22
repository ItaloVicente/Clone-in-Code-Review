		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		final RevCommit id = git.commit().setMessage("create file").call();
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		assertNotNull(Git.init().setDirectory(new File(db.getWorkTree(), path))
				.call().getRepository());

		TreeWalk walk = new TreeWalk(db);
		DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
		FileTreeIterator workTreeIter = new FileTreeIterator(db);
		walk.addTree(indexIter);
		walk.addTree(workTreeIter);
		walk.setFilter(PathFilter.create(path));

		assertTrue(walk.next());
		assertFalse(indexIter.idEqual(workTreeIter));
		assertEquals(ObjectId.zeroId(), workTreeIter.getEntryObjectId());
