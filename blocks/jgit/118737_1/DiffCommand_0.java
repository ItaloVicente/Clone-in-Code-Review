	private DiffFormatter getDiffFormatter() {
		return out != null && !showNameAndStatusOnly
				? new DiffFormatter(new BufferedOutputStream(out))
				: new DiffFormatter(NullOutputStream.INSTANCE);
	}

