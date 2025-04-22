
		if (smudge) {
			DirCache dc = DirCache.lock(git.getRepository().getIndexFile()
					FS.detect());
			DirCacheEditor editor = dc.editor();
			for (int i = 0; i < dc.getEntryCount(); i++) {
				editor.add(new DirCacheEditor.PathEdit(
						dc.getEntry(i).getPathString()) {
					public void apply(DirCacheEntry ent) {
						ent.smudgeRacilyClean();
					}
				});
			}
			editor.commit();
		}

		git.checkout().setName(c.getName()).call();
		git.checkout().setName("master").call();
