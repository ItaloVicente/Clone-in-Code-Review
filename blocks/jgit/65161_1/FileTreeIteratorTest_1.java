		try (Git git = new Git(db)) {
			fsTick(f);
			writeTrashFile("file"
			long lastModified = f.lastModified();
			git.add().addFilepattern("file").call();
			writeTrashFile("file"
			f.setLastModified(lastModified);
			db.getIndexFile().setLastModified(lastModified);
		}
