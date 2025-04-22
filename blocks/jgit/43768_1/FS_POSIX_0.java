public class FS_POSIX extends FS {

	static {
		String umask = readUmask();

		if (umask != null && umask.length() > 0 && umask.matches("\\d{3
			EXECUTE_FOR_OTHERS = isGranted(PosixFilePermission.OTHERS_EXECUTE
					umask);
			EXECUTE_FOR_GROUP = isGranted(PosixFilePermission.GROUP_EXECUTE
					umask);
		} else {
			EXECUTE_FOR_OTHERS = null;
			EXECUTE_FOR_GROUP = null;
		}
	}

	protected static final Boolean EXECUTE_FOR_OTHERS;

	protected static final Boolean EXECUTE_FOR_GROUP;

	@Override
	public FS newInstance() {
		return new FS_POSIX();
	}

	protected static Boolean isGranted(PosixFilePermission p
		char val;
		switch (p) {
		case OTHERS_EXECUTE:
			val = umask.charAt(umask.length() - 1);
			return isExecuteGranted(val);
		case GROUP_EXECUTE:
			val = umask.charAt(umask.length() - 2);
			return isExecuteGranted(val);
		default:
			throw new UnsupportedOperationException(
		}
	}

	private static Boolean isExecuteGranted(char c) {
		if (c == '0' || c == '2' || c == '4' || c == '6')
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	protected static String readUmask() {
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					new String[] { "sh"
			try (BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream()
							.defaultCharset().name()))) {
				p.waitFor();
				return lineRead.readLine();
			}
		} catch (Exception e) {
			return null;
		}
	}

