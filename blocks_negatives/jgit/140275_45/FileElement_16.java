			File file = new File(path);
			if (directory != null) {
				tempFile = getTempFile(file, directory, type.name());
			} else {
				tempFile = getTempFile(file);
			}
