
	public static String format(byte[] delta) {
		return format(delta
	}

	public static String format(byte[] delta
		StringBuilder r = new StringBuilder();
		int deltaPtr = 0;

		long baseLen = 0;
		int c
		do {
			c = delta[deltaPtr++] & 0xff;
			baseLen |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		long resLen = 0;
		shift = 0;
		do {
			c = delta[deltaPtr++] & 0xff;
			resLen |= (c & 0x7f) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		if (includeHeader)
			r.append("DELTA( BASE=" + baseLen + " RESULT=" + resLen + " )\n");

		while (deltaPtr < delta.length) {
			final int cmd = delta[deltaPtr++] & 0xff;
			if ((cmd & 0x80) != 0) {
				int copyOffset = 0;
				if ((cmd & 0x01) != 0)
					copyOffset = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x02) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x04) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 16;
				if ((cmd & 0x08) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 24;

				int copySize = 0;
				if ((cmd & 0x10) != 0)
					copySize = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x20) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x40) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 16;
				if (copySize == 0)
					copySize = 0x10000;

				r.append("  COPY  (" + copyOffset + "

			} else if (cmd != 0) {
				r.append("  INSERT(");
				r.append(QuotedString.GIT_PATH.quote(RawParseUtils.decode(
						delta
				r.append(")\n");
				deltaPtr += cmd;
			} else {
				throw new IllegalArgumentException(
						JGitText.get().unsupportedCommand0);
			}
		}

		return r.toString();
	}
