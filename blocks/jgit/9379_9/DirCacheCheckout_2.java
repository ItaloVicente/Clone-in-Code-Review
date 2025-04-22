		WorkingTreeOptions opt = repo.getConfig().get(WorkingTreeOptions.KEY);
		if (entry.getFileMode() == FileMode.SYMLINK
				&& opt.getSymLinks() == SymLinks.TRUE) {
			byte[] bytes = ol.getBytes();
			String target = RawParseUtils.decode(bytes);
			fs.createSymLink(f
			entry.setLength(bytes.length);
			entry.setLastModified(fs.lastModified(f));
		} else {
			File tmpFile = File.createTempFile(
					"._" + f.getName()
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
			if (opt.isFileMode() && fs.supportsExecute()) {
				if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
					if (!fs.canExecute(tmpFile))
						fs.setExecute(tmpFile
				} else {
					if (fs.canExecute(tmpFile))
						fs.setExecute(tmpFile
				}
