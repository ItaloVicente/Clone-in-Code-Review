	static HttpAuthMethod scanResponse(HttpURLConnection conn) {
		String hdr = conn.getHeaderField(HDR_WWW_AUTHENTICATE);
		if (hdr == null || hdr.length() == 0)
			return NONE;

		int sp = hdr.indexOf(' ');
		if (sp < 0)
			return NONE;

		String type = hdr.substring(0, sp);
		if (Basic.NAME.equalsIgnoreCase(type))
			return new Basic();
		else if (Digest.NAME.equalsIgnoreCase(type))
			return new Digest(hdr.substring(sp + 1));
		else
			return NONE;
