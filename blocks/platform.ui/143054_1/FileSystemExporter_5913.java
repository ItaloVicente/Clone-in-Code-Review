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
