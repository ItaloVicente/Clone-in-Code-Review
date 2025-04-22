	private static Optional<PackFile> toPackFileWithValidExt(
			Path packFilePath) {
		try {
			PackFile packFile = new PackFile(packFilePath.toFile());
			if (packFile.getPackExt() == null) {
				return Optional.empty();
			}
			return Optional.of(packFile);
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

