		Git git = new Git(db);
		writeTrashFile(db, "a.txt", "trash");
		writeTrashFile(db, "b.txt", "trash");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		git.commit().setMessage("new commit").call();
		git.rm().addFilepattern("a.txt").addFilepattern("b.txt").call();
