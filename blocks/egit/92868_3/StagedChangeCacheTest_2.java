		try (Git git = new Git(db)) {
			writeTrashFile(db, "folder/a.txt", "trash");
			git.add().addFilepattern("folder/a.txt").call();
			git.commit().setMessage("new commit").call();
			git.rm().addFilepattern("folder/a.txt").call();
		}
