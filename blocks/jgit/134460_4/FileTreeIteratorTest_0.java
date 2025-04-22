		File file = new File(trash

		try {
			file.toPath();
		} catch (InvalidPathException e) {
			assumeNoException(e);
		}

		fs.createSymLink(file
