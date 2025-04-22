		Git git = new Git(db);
		writeTrashFile(db, "folder/a.txt", "trash");
		writeTrashFile(db, "folder/b.txt", "trash");
		git.add().addFilepattern("folder/a.txt").addFilepattern("folder/b.txt").call();
		git.commit().setMessage("new commit").call();
		git.rm().addFilepattern("folder/a.txt").call();
		git.rm().addFilepattern("folder/b.txt").call();
