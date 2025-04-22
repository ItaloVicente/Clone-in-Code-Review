		try (PrintWriter nestedFileWriter = new PrintWriter(nestedFile)) {
			nestedFileWriter.print("content");
			nestedFileWriter.flush();

			indexFile = new File(db.getWorkTree()
			FileUtils.createNewFile(indexFile);
			try (PrintWriter writer = new PrintWriter(indexFile)) {
				writer.print("content");
				writer.flush();

				git.add().addFilepattern("dir").addFilepattern("a.txt").call();
				secondCommit = git.commit()
						.setMessage("adding a.txt and dir/b.txt").call();

				prestage = DirCache.read(db.getIndexFile()
						.getEntry(indexFile.getName());

				writer.print("new content");
			}
			nestedFileWriter.print("new content");
		}
