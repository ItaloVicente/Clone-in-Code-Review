	public boolean supportsExecute() {
		return true;
	}

	@Override
	public boolean canExecute(File f) {
		return FileUtil.canExecute(f);
	}

	@Override
	public boolean setExecute(File f
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
