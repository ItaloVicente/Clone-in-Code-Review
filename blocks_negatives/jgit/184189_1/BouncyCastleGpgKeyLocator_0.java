			if (appData != null && !appData.isEmpty()) {
				try {
					if (Files.isDirectory(directory)) {
						return directory;
					}
				} catch (SecurityException | InvalidPathException e) {
				}
