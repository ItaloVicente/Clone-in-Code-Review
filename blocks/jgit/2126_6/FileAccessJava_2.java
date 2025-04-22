
	@Override
	public DirEnt[] list(File dir) throws AccessDeniedException {
		String[] names = dir.list();
		if (names == null)
			throw new AccessDeniedException(dir.getPath());

		DirEnt[] r = new DirEnt[names.length];
		for (int i = 0; i < names.length; i++)
			r[i] = new DirEnt(names[i]);
		return r;
	}
