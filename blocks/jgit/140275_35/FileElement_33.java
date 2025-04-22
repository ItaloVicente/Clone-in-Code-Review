	private static File getTempFile(final File file
			final File workingDir) throws IOException {
		String[] fileNameAndExtension = splitBaseFileNameAndExtension(file);
		return File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_"
				fileNameAndExtension[1]
	}

	private static void copyFromStream(final File file
			final InputStream stream)
			throws IOException
		try (OutputStream outStream = new FileOutputStream(file)) {
			int read = 0;
			byte[] bytes = new byte[8 * 1024];
			while ((read = stream.read(bytes)) != -1) {
				outStream.write(bytes
			}
		} finally {
			stream.close();
		}
	}

	private static String[] splitBaseFileNameAndExtension(File file) {
		String[] result = new String[2];
		result[0] = file.getName();
		if (idx > 0) {
			result[1] = result[0].substring(idx
			result[0] = result[0].substring(0
		}
		return result;
	}

