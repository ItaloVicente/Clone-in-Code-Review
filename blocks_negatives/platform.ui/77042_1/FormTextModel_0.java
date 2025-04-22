		try {
			InputStream stream = new ByteArrayInputStream(taggedText
			parseInputStream(stream, expandURLs);
		} catch (UnsupportedEncodingException e) {
			SWT.error(SWT.ERROR_UNSUPPORTED_FORMAT, e);
		}
