			int redirects = 0;
			for (;;) {
				openStream();
				if (buf != out) {
					conn.setRequestProperty(HDR_CONTENT_ENCODING
				}
				conn.setFixedLengthStreamingMode((int) buf.length());
				try (OutputStream httpOut = conn.getOutputStream()) {
					buf.writeTo(httpOut
				}
