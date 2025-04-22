			openStream(redirectUrl);
			if (buf != out)
				conn.setRequestProperty(HDR_CONTENT_ENCODING, ENCODING_GZIP);
			conn.setFixedLengthStreamingMode((int) buf.length());
			final OutputStream httpOut = conn.getOutputStream();
			try {
				buf.writeTo(httpOut, null);
			} finally {
				httpOut.close();
