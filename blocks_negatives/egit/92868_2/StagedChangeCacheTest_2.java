		Git git = new Git(db);
		writeTrashFile(db, "a.txt", "trash");
		git.add().addFilepattern("a.txt").call();
		git.commit().setMessage("initial a.txt commit").call();
		writeTrashFile(db, "a.txt", "modification");
		git.add().addFilepattern("a.txt").call();
