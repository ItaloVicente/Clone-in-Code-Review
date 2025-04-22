		Git git = Git.wrap(db);

		writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName, fname).toFile();
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();
