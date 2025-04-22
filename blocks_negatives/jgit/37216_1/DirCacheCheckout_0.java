		} else {
			File tmpFile = File.createTempFile(
					"._" + f.getName(), null, parentDir); //$NON-NLS-1$
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
						fs.setExecute(tmpFile, true);
				} else {
					if (fs.canExecute(tmpFile))
						fs.setExecute(tmpFile, false);
				}
			}
			try {
				FileUtils.rename(tmpFile, f);
			} catch (IOException e) {
				throw new IOException(MessageFormat.format(
						JGitText.get().renameFileFailed, tmpFile.getPath(),
						f.getPath()));
