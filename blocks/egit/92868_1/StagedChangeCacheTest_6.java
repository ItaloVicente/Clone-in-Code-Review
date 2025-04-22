		try (Git git = new Git(db)) {
			writeTrashFile(db, "folder/a.txt", "trash");
			git.add().addFilepattern("folder/a.txt").call();
			git.commit().setMessage("new commit").call();
			writeTrashFile(db, "folder/a.txt", "modification");
			git.add().addFilepattern("folder/a.txt").call();
		}
