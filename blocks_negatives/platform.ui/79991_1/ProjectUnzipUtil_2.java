		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipLocation.toFile());
		} catch (IOException e1) {
			throw e1;
		}
		try {
			Enumeration entries = zipFile.entries();
