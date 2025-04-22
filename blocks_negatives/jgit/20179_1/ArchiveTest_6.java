		writeTrashFile("plain", "a file with content");
		writeTrashFile("executable", "an executable file");
		writeTrashFile("symlink", "plain");
		writeTrashFile("dir/content", "clutter in a subdir");
		git.add().addFilepattern("plain").call();
		git.add().addFilepattern("executable").call();
		git.add().addFilepattern("symlink").call();
		git.add().addFilepattern("dir").call();

		DirCache cache = db.lockDirCache();
		cache.getEntry("executable").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.getEntry("symlink").setFileMode(FileMode.SYMLINK);
		cache.write();
		cache.commit();
		cache.unlock();

		git.commit().setMessage("three files with different modes").call();

