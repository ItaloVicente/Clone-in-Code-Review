		writeTrashFile("dir/b.txt"

		git.add().addFilepattern("a.txt").addFilepattern("dir/b.txt").call();
		secondCommit = git.commit().setMessage("adding a.txt and dir/b.txt").call();

		prestage = DirCache.read(db.getIndexFile()

		writeTrashFile("a.txt"
		writeTrashFile("dir/b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("dir/b.txt").call();
