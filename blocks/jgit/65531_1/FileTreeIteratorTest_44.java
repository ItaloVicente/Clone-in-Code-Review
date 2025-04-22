		try (Git git = new Git(db);
				TreeWalk walk = new TreeWalk(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			final RevCommit id = git.commit().setMessage("create file").call();
			final String path = "sub/dir1/dir2";
			DirCache cache = db.lockDirCache();
			DirCacheEditor editor = cache.editor();
			editor.add(new PathEdit(path) {

				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.GITLINK);
					ent.setObjectId(id);
				}
			});
			editor.commit();

			Git.cloneRepository().setURI(db.getDirectory().toURI().toString())
					.setDirectory(new File(db.getWorkTree()
					.getRepository().close();

			DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
			FileTreeIterator workTreeIter = new FileTreeIterator(db);
			walk.addTree(indexIter);
			walk.addTree(workTreeIter);
			walk.setFilter(PathFilter.create(path));

			assertTrue(walk.next());
			assertTrue(indexIter.idEqual(workTreeIter));
		}
