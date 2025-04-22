		if (!canExecute)
			return f.setExecutable(false);

		try {
			Path path = f.toPath();
			Set<PosixFilePermission> pset = Files.getPosixFilePermissions(path);

			pset.add(PosixFilePermission.OWNER_EXECUTE);

			int mask = umask();
			if ((mask & (1 << 3)) == 0)
				pset.add(PosixFilePermission.GROUP_EXECUTE);
			if ((mask & 1) == 0)
				pset.add(PosixFilePermission.OTHERS_EXECUTE);
			Files.setPosixFilePermissions(path
			return true;
		} catch (IOException e) {
			final boolean debug = Boolean.parseBoolean(SystemReader
			if (debug)
				System.err.println(e);
			return false;
