		void sendRequest() throws IOException {
			TemporaryBuffer buf = new TemporaryBuffer.Heap(http.postBuffer);
			try {
				GZIPOutputStream gzip = new GZIPOutputStream(buf);
				out.writeTo(gzip
				gzip.close();
				if (out.length() < buf.length())
