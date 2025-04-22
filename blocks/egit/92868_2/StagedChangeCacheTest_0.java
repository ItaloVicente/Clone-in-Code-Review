		try (Git git = new Git(db)) {
			writeTrashFile(db, "a.txt", "trash");
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("initial add").call();
			git.rm().addFilepattern("a.txt").call();
		}
