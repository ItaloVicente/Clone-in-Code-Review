			if (stream != null) {
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
			}
