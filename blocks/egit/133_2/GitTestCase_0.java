	@Override
	protected List<File> getCeilings() {
		List<File> ret = new ArrayList<File>(super.getCeilings());
		ret.add(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getAbsoluteFile());
		return ret;
	}

