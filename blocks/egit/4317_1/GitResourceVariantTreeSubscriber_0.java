		if (res == null)
			return false;
		IProject proj = res.getProject();
		if (proj == null)
			return false;
		Set<IContainer> includedPaths = gsds.getData(proj).getIncludedPaths();
