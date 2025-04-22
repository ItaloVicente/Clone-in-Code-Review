	public static File normalize(File file) {
		if (SystemReader.getInstance().isMacOS()) {
			String normalized = Normalizer.normalize(file.getPath()
					Normalizer.Form.NFC);
			return new File(normalized);
		}
		return file;
	}

	public static String normalize(String name) {
		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			return Normalizer.normalize(name
		}
		return name;
	}

