		FileUtils.mkdir(refsFile

		OutputStream os = new FileOutputStream(headFile);
		os.write(Constants.encodeASCII("ref: refs/heads/.invalid"));
		os.close();

		FileUtils.createNewFile(new File(refsFile
