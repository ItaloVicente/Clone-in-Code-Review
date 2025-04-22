		File dir = new File(db.getWorkTree()
		FileUtils.mkdir(dir);
		File nestedFile = new File(dir
		FileUtils.createNewFile(nestedFile);

		PrintWriter nesterFileWriter = new PrintWriter(nestedFile);
		nesterFileWriter.print("content");
		nesterFileWriter.flush();

