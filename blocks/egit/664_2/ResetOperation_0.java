	public ISchedulingRule getSchedulingRule() {
		if (type == ResetType.HARD)
			return ResourcesPlugin.getWorkspace().getRoot();
		else
			return null;
	}


