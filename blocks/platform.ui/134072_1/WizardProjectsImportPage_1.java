	private static boolean check(java.nio.file.Path file, BasicFileAttributes attribute) {
		String fileName = file.getFileName().toString();
		return ((IProjectDescription.DESCRIPTION_FILE_NAME.equals(fileName) && !attribute.isDirectory())
				&& METADATA_FOLDER.equals(fileName));
	}

