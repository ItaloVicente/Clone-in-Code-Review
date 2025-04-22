		filename = filename + "1234567890";
		l.add(filename);
		writeTrashFile(filename, "file with long path");
		git.add().addFilepattern("1234567890").call();
		git.commit().setMessage("file with long name").call();

				"git archive --format=tar HEAD", db);
		assertArrayEquals(l.toArray(new String[l.size()]),
				listTarEntries(result));
