		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
	}

	private List<IResource> getSelectedResources() {
		List<IResource> resources = new ArrayList<IResource>();
		for(Object object: selection) {
			if(object instanceof IResource)
				resources.add((IResource) object);
