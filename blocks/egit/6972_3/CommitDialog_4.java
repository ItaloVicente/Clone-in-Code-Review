	private static int getProblemSeverity(Repository repository, String path) {
		IFile file = ResourceUtil.getFileForLocation(repository, path);
		if (file != null) {
			try {
				int severity = file.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ONE);
				return severity;
			} catch (CoreException e) {
			}
		}
		return IProblemDecoratable.SEVERITY_NONE;
	}

