		if (SystemReader.getInstance().isMacOS()) {
			String normalized = Normalizer.normalize(file.getPath(),
					Normalizer.Form.NFC);
			return new File(normalized);
		}
		return file;
