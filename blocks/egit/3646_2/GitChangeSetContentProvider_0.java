	@Override
	public void diffsChanged(IDiffChangeEvent event, IProgressMonitor monitor) {
		List<IPath> toRefresh = new LinkedList<IPath>();
		for (IDiff addition : event.getAdditions())
			toRefresh.add(addition.getPath());
		for (IDiff changed : event.getChanges())
			toRefresh.add(changed.getPath());


		toRefresh.addAll(Arrays.asList(event.getRemovals()));
		for (IPath refresh : toRefresh)
			for (GitModelObject child : modelRoot.getChildren())
				if (child instanceof GitModelCache)
					for (IProject project : child.getProjects())
						if (project.getName().equals(refresh.segment(0))) {
							child.refresh();
							break;
						}

		super.diffsChanged(event, monitor);
	}

