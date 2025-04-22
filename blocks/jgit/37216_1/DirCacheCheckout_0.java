			return;
		}

		File tmpFile = File.createTempFile(
				"._" + f.getName()
		OutputStream channel = new FileOutputStream(tmpFile);
		if (opt.getAutoCRLF() == AutoCRLF.TRUE)
			channel = new AutoCRLFOutputStream(channel);
		try {
			ol.copyTo(channel);
		} finally {
			channel.close();
		}
		entry.setLength(opt.getAutoCRLF() == AutoCRLF.TRUE
		    : (int) ol.getSize());

		if (opt.isFileMode() && fs.supportsExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
				if (!fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
			} else {
				if (fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
