		String tag = element.getName();

		if (tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
			((ObjectContribution) currentContribution).setVisibilityTest(element);
			return true;
		}

		if (tag.equals(IWorkbenchRegistryConstants.TAG_FILTER)) {
			((ObjectContribution) currentContribution).addFilterTest(element);
			return true;
		}

		if (tag.equals(IWorkbenchRegistryConstants.TAG_ENABLEMENT)) {
			((ObjectContribution) currentContribution).setEnablementTest(element);
