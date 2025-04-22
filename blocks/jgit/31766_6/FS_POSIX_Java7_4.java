		if (!isFile(f))
			return false;
		if (canExecute && EXECUTE_FOR_OTHERS != null) {
			try {
				Path path = f.toPath();
				Set<PosixFilePermission> pset = Files
						.getPosixFilePermissions(path);
				pset.add(PosixFilePermission.OWNER_EXECUTE);

				if (EXECUTE_FOR_GROUP.booleanValue())
					pset.add(PosixFilePermission.GROUP_EXECUTE);

				if (EXECUTE_FOR_OTHERS.booleanValue())
					pset.add(PosixFilePermission.OTHERS_EXECUTE);

				Files.setPosixFilePermissions(path
				return true;
			} catch (IOException e) {
				final boolean debug = Boolean.parseBoolean(SystemReader
				if (debug)
					System.err.println(e);
				return false;
			}
		}
		return f.setExecutable(canExecute);
	}

	private static Boolean isGranted(PosixFilePermission p
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

	private static String readUmask() {
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
