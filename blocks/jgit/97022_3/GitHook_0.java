		PrintStream hookErrRedirect = null;
		try {
			hookErrRedirect = new PrintStream(errorByteArray
					UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
