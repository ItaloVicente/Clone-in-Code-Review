	public static File normalize(File file) {
		if (SystemReader.getInstance().isMacOS()) {
			if (System.getProperties().getProperty("java.version")
					.startsWith("1.7")) {
				String normalized = Normalizer.normalize(file.getPath()
						Normalizer.Form.NFC);
				return new File(normalized);
			}
		}
		return file;
	}

