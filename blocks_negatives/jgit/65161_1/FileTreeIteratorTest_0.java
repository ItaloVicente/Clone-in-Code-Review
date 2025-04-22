		Git git = new Git(db);
		fsTick(f);
		writeTrashFile("file", "content");
		long lastModified = f.lastModified();
		git.add().addFilepattern("file").call();
		writeTrashFile("file", "conten2");
		f.setLastModified(lastModified);
		db.getIndexFile().setLastModified(lastModified);
