		File dir = new File(db.getWorkTree(), "dir");
		FileUtils.mkdir(dir);
		File nestedFile = new File(dir, "b.txt");
		FileUtils.createNewFile(nestedFile);

		try (PrintWriter nestedFileWriter = new PrintWriter(nestedFile)) {
			nestedFileWriter.print("content");
			nestedFileWriter.flush();

			indexFile = new File(db.getWorkTree(), "a.txt");
			FileUtils.createNewFile(indexFile);
			try (PrintWriter writer = new PrintWriter(indexFile)) {
				writer.print("content");
				writer.flush();

				git.add().addFilepattern("dir").addFilepattern("a.txt").call();
				secondCommit = git.commit()
						.setMessage("adding a.txt and dir/b.txt").call();

				prestage = DirCache.read(db.getIndexFile(), db.getFS())
						.getEntry(indexFile.getName());

				writer.print("new content");
			}
			nestedFileWriter.print("new content");
		}
		git.add().addFilepattern("a.txt").addFilepattern("dir").call();
