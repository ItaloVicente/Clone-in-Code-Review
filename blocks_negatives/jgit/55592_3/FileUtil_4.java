		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			return Normalizer.normalize(name, Normalizer.Form.NFC);
		}
		return name;
