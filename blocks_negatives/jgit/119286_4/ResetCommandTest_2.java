		PrintWriter nesterFileWriter = new PrintWriter(nestedFile);
		nesterFileWriter.print("content");
		nesterFileWriter.flush();

		indexFile = new File(db.getWorkTree(), "a.txt");
		FileUtils.createNewFile(indexFile);
		PrintWriter writer = new PrintWriter(indexFile);
		writer.print("content");
		writer.flush();

		git.add().addFilepattern("dir").addFilepattern("a.txt").call();
		secondCommit = git.commit().setMessage("adding a.txt and dir/b.txt")
				.call();

		prestage = DirCache.read(db.getIndexFile(), db.getFS()).getEntry(
				indexFile.getName());

		writer.print("new content");
		writer.close();
		nesterFileWriter.print("new content");
		nesterFileWriter.close();
