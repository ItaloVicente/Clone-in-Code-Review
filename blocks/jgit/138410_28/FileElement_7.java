	private void copyFromStream() throws IOException
		if (stream != null) {
			try (OutputStream outStream = new FileOutputStream(tempFile)) {
				int read = 0;
				byte[] bytes = new byte[8 * 1024];
				while ((read = stream.read(bytes)) != -1) {
					outStream.write(bytes
				}
			} finally {
				stream.close();
				stream = null;
			}
		}
	}

	private static String[] splitBaseFileNameAndExtension(File file) {
		String[] result = new String[2];
		result[0] = file.getName();
			if (idx != -1) {
				result[1] = result[0].substring(idx
				result[0] = result[0].substring(0
			}
		}
		return result;
	}

