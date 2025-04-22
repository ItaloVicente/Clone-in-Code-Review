			if (mode == FileMode.SYMLINK) {
				try {
					return FS.DETECTED.lastModified(asFile());
				} catch (IOException e) {
					return 0;
				}
			}
