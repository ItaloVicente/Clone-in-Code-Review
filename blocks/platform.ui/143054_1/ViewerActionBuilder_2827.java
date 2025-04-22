		String tag = element.getName();

		if (currentContribution != null && tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
			((ViewerContribution) currentContribution).setVisibilityTest(element);
			return true;
		}

		return super.readElement(element);
	}

	public boolean readViewerContributions(String id, ISelectionProvider prov, IWorkbenchPart part) {
