		if (!canExecute)
			return f.setExecutable(false);

		try {
			Path path = f.toPath();
			Set<PosixFilePermission> pset = Files.getPosixFilePermissions(path);

			pset.add(PosixFilePermission.OWNER_EXECUTE);

			int mask = umask();
			apply(pset
			apply(pset
			Files.setPosixFilePermissions(path
			return true;
		} catch (IOException e) {
			final boolean debug = Boolean.parseBoolean(SystemReader
			if (debug)
				System.err.println(e);
			return false;
		}
	}

	private static void apply(Set<PosixFilePermission> set
			int umask
		if ((umask & test) == 0) {
			set.add(perm);
		} else {
			set.remove(perm);
