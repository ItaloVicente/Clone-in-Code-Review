	@Override
	public File resolve(File dir
		final File abspn = createFile(name);
		if (abspn.isAbsolute())
			return abspn;
		return createFile(dir
	}

