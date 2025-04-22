			openStream();
			if (buf != out)
				conn.setRequestProperty(HDR_CONTENT_ENCODING
			conn.setFixedLengthStreamingMode((int) buf.length());
			final OutputStream httpOut = conn.getOutputStream();
			try {
				buf.writeTo(httpOut
			} finally {
				httpOut.close();
			}
		}
