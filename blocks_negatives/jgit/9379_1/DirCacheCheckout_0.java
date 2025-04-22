		FileOutputStream rawChannel = new FileOutputStream(tmpFile);
		OutputStream channel;
		if (opt.getAutoCRLF() == AutoCRLF.TRUE)
			channel = new AutoCRLFOutputStream(rawChannel);
		else
			channel = rawChannel;
		try {
			ol.copyTo(channel);
		} finally {
			channel.close();
		}
		FS fs = repo.getFS();
		if (opt.isFileMode() && fs.supportsExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
				if (!fs.canExecute(tmpFile))
					fs.setExecute(tmpFile, true);
			} else {
				if (fs.canExecute(tmpFile))
					fs.setExecute(tmpFile, false);
