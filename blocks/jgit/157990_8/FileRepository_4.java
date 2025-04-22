		FileUtils.mkdir(refsFile

		try (OutputStream os = new FileOutputStream(headFile)) {
			os.write(Constants.encodeASCII("ref: refs/heads/.invalid"));
		}

		FileUtils.createNewFile(new File(refsFile
