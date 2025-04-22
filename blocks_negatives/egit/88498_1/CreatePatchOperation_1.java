		try {
			String encoding = currentEncoding != null ? currentEncoding
					: RawParseUtils.UTF8_CHARSET.name();
			sb.append(outputStream.toString(encoding));
		} catch (UnsupportedEncodingException e) {
			sb.append(outputStream.toString());
		}
