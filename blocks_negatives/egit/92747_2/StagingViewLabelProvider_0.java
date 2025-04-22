			IPath diffLocation = diff.getLocation();
			if (diffLocation != null) {
				File diffFile = diffLocation.toFile();
				if (diffFile.isDirectory()) {
					image = FOLDER;
				}
