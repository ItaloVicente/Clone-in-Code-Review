			File file = new File(path);
			if (workingDir != null) {
				tempFile = getTempFile(file, workingDir, type.name());
			} else {
				tempFile = getTempFile(file);
			}
