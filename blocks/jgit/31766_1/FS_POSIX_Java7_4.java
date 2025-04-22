	private static Boolean isSet(PosixFilePermission p
		char val;
		switch (p) {
		case OTHERS_EXECUTE:
			val = umask.charAt(umask.length() - 1);
			return fromExecuteBit(val);
		default:
			throw new UnsupportedOperationException(
		}
	}

	private static String readUmask() {
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					new String[] { "sh"
			final BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream()
							.defaultCharset().name()));
			p.waitFor();
			return lineRead.readLine();
		} catch (Exception e) {
			return null;
		}
	}

	private static Boolean fromExecuteBit(char val) {
		if (val == '0' || val == '2' || val == '4' || val == '6')
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

