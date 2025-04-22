		boolean created = false;
		try {
			Files.createFile(file.toPath());
			created = true;
		} catch (FileAlreadyExistsException e) {
		}
		if (!created) {
