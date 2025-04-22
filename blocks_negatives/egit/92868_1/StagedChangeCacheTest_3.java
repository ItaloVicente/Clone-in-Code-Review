		Git git = new Git(db);
		writeTrashFile(db, "a.txt", "trash");
		writeTrashFile(db, "b.txt", "trash");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		git.commit().setMessage("new commmit").call();
		writeTrashFile(db, "a.txt", "modification");
		writeTrashFile(db, "b.txt", "modification");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
