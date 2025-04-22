	public Object getInput() {
		final ResourceList r = (ResourceList) super.getInput();
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
			in = new ResourceList(new IResource[] { (IResource) o });
		else if (o instanceof ResourceList)
			in = o;
		else if (o instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) o)
					.getAdapter(IResource.class);
			in = resource == null ? null : new ResourceList(
					new IResource[] { resource });
		} else
			in = null;
		return super.setInput(in);
