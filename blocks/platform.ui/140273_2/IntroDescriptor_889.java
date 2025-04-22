	public IntroContentDetector getIntroContentDetector() throws CoreException {
		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_CONTENT_DETECTOR) == null) {
			return null;
		}
		return (IntroContentDetector) element
				.createExecutableExtension(IWorkbenchRegistryConstants.ATT_CONTENT_DETECTOR);
	}
