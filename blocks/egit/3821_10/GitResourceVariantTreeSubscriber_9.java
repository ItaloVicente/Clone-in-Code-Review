	@Override
	public void refresh(IResource[] resources, int depth,
			IProgressMonitor monitor) throws TeamException {
		init(monitor);
		super.refresh(resources, depth, monitor);
	}

