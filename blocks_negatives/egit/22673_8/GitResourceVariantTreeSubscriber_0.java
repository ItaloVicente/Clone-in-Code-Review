		if (roots == null)
			roots = gsds.getAllProjects();
		if (roots == null)
			return new IResource[0];
		IResource[] result = new IResource[roots.length];
		System.arraycopy(roots, 0, result, 0, roots.length);
		return result;
