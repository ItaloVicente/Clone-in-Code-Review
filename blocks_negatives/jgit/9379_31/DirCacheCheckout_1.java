		if (opt.isFileMode() && fs.supportsExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
				if (!fs.canExecute(tmpFile))
					fs.setExecute(tmpFile, true);
			} else {
				if (fs.canExecute(tmpFile))
					fs.setExecute(tmpFile, false);
