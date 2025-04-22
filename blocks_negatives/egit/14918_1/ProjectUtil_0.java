		String absPrj = container.getLocation().toFile().getAbsolutePath();
		if (absPrj.equals(absFile))
			return true;
		if (absPrj.length() < absFile.length()) {
			char sepChar = absFile.charAt(absPrj.length());
			if (sepChar == File.separatorChar && absFile.startsWith(absPrj))
