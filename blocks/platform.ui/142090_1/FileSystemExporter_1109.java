		try {
			contentStream = new BufferedInputStream(file.getContents(false));
			output = new BufferedOutputStream(new FileOutputStream(destinationPath.toOSString()));
			int available = contentStream.available();
			available = available <= 0 ? DEFAULT_BUFFER_SIZE : available;
			int chunkSize = Math.min(DEFAULT_BUFFER_SIZE, available);
			byte[] readBuffer = new byte[chunkSize];
			int n = contentStream.read(readBuffer);
