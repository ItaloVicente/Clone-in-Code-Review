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
				.setDirectory(new File(db.getWorkTree(), path)).call()
				.getRepository().close();
