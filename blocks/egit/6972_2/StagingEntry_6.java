	public int getProblemSeverity() {
		try {
			return getFile().findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_ONE);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

