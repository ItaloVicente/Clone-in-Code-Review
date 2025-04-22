		void execute() throws IOException {
			out.close();

			if (conn == null) {
				if (out.length() == 0) {
					if (finalRequest)
						return;
					throw new TransportException(uri,
							JGitText.get().startingReadStageWithoutWrittenRequestDataPendingIsNotSupported);
				}

				TemporaryBuffer buf = new TemporaryBuffer.Heap(http.postBuffer);
				try {
					GZIPOutputStream gzip = new GZIPOutputStream(buf);
					out.writeTo(gzip, null);
					gzip.close();
					if (out.length() < buf.length())
						buf = out;
				} catch (IOException err) {
