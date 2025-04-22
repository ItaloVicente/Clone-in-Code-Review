		RawText rt;
		try {
			rt = new RawText(sb.toString().getBytes("UTF-8")); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
