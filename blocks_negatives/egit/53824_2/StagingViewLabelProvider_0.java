			try {
				IPath diffLocation = diff.getLocation();
				if (diffLocation != null) {
					File diffFile = diffLocation.toFile();
					if (diffFile.exists()) {
						String targetPath = FS.DETECTED.readSymLink(diffFile);
						if (targetPath != null
								&& new File(diffFile, targetPath).isDirectory()) {
							image = FOLDER;
						}
					}
