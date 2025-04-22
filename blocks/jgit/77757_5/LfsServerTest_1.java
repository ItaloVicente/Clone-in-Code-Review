			String error;
			try {
				ByteBuffer buf = IO.readWholeStream(new BufferedInputStream(
						response.getEntity().getContent())
				error = new String(buf.array()
			} catch (IOException e) {
				error = statusLine.getReasonPhrase();
			}
			throw new RuntimeException("Status: " + status + " " + error);
