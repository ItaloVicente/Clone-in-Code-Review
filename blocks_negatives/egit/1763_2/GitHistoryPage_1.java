	public Object getInput() {
		final HistoryPageInput r = (HistoryPageInput) super.getInput();
		if (r == null)
			return null;
		final IResource[] in = r.getItems();
		if (in == null || in.length == 0)
			return null;
		if (in.length == 1)
			return in[0];
		return r;
	}

	public boolean setInput(final Object o) {
		final Object in;
		if (o instanceof IResource)
			in = new HistoryPageInput(RepositoryMapping.getMapping(
					(IResource) o).getRepository(),
					new IResource[] { (IResource) o });
		else if (o instanceof HistoryPageInput)
			in = o;
		else
			in = null;
		return super.setInput(in);
