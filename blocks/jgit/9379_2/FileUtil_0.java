		String targetString = target.toString();
		if (SystemReader.getInstance().isWindows())
			targetString = targetString.replace('\\'
		else if (SystemReader.getInstance().isMacOS()) {
			targetString = Normalizer.normalize(targetString
		}
		return targetString;
