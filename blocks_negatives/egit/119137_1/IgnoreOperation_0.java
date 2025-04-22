	/**
	 * @param resources
	 * @deprecated use {@link #IgnoreOperation(Collection)}
	 */
	@Deprecated
	public IgnoreOperation(IResource[] resources) {
		paths = new ArrayList<IPath>(resources.length);
		for (IResource resource : resources) {
			IPath location = resource.getLocation();
			if (location != null)
				paths.add(location);
		}
		gitignoreOutsideWSChanged = false;
		schedulingRule = calcSchedulingRule();
	}

