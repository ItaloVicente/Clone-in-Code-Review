			throws IOException, CoreException {
		OutputStream output = null;
		InputStream contentStream = null;

		try {
			contentStream = new BufferedInputStream(file.getContents(false));
			output = new BufferedOutputStream(new FileOutputStream(destinationPath.toOSString()));
			int available = contentStream.available();
			available = available <= 0 ? DEFAULT_BUFFER_SIZE : available;
			int chunkSize = Math.min(DEFAULT_BUFFER_SIZE, available);
			byte[] readBuffer = new byte[chunkSize];
			int n = contentStream.read(readBuffer);

			while (n > 0) {
				output.write(readBuffer, 0, n);
				n = contentStream.read(readBuffer);
			}
		} finally {
			if (contentStream != null) {
				try {
					contentStream.close();
				}
				catch(IOException e){
					IDEWorkbenchPlugin
							.log(
									"Error closing input stream for file: " + file.getLocation(), e); //$NON-NLS-1$
				}
			}
			if (output != null) {
				output.close();
			}
		}
