	void performFinish(IProgressMonitor monitor) {

		IMarkerResolution resolution = (IMarkerResolution) resolutionsList.getStructuredSelection()
				.getFirstElement();
		if (resolution == null) {
			return;
		}
