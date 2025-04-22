		final byte[] out;
		final String text = toText();
		if (utf8Bom) {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bos.write(0xEF);
			bos.write(0xBB);
			bos.write(0xBF);
			bos.write(text.getBytes(RawParseUtils.UTF8_CHARSET));
			out = bos.toByteArray();
		} else {
			out = Constants.encode(text);
		}

