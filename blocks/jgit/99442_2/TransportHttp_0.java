			int redirects = 0;
			Collection<Type> ignoreTypes = EnumSet.noneOf(Type.class);
			int authAttempts = 0;
			int negotiationRounds = 0;
			for (;;) {
				openStream();
				if (buf != out) {
					conn.setRequestProperty(HDR_CONTENT_ENCODING
							ENCODING_GZIP);
				}
				conn.setFixedLengthStreamingMode((int) buf.length());
				try (OutputStream httpOut = conn.getOutputStream()) {
					buf.writeTo(httpOut
				}
