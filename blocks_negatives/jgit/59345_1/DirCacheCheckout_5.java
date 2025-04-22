		File tmpFile = File.createTempFile(
				"._" + f.getName(), null, parentDir); //$NON-NLS-1$
		OutputStream channel = new FileOutputStream(tmpFile);
		if (opt.getAutoCRLF() == AutoCRLF.TRUE)
			channel = new AutoCRLFOutputStream(channel);
		try {
