		InputStream contentStream = contents.getContents(false);
		entry.setSize(EFS.getStore(location).fetchInfo().getLength());
		outputStream.putNextEntry(entry);
		try {
			int n;
			byte[] readBuffer = new byte[4096];
			while ((n = contentStream.read(readBuffer)) > 0) {
				outputStream.write(readBuffer, 0, n);
			}
		} finally {
			if (contentStream != null) {
