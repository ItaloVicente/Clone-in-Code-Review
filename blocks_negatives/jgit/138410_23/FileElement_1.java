		File file = new File(path);
		String name = file.getName();
		if (path.equals(DiffEntry.DEV_NULL)) {
			file = new File(workingDir, "nul"); //$NON-NLS-1$
		}
		else if (stream != null) {
			tempFile = File.createTempFile(".__", "__" + name); //$NON-NLS-1$ //$NON-NLS-2$
			try (OutputStream outStream = new FileOutputStream(tempFile)) {
				int read = 0;
				byte[] bytes = new byte[8 * 1024];
				while ((read = stream.read(bytes)) != -1) {
					outStream.write(bytes, 0, read);
				}
			} finally {
				stream.close();
				stream = null;
			}
			return tempFile;
		}
		return file;
