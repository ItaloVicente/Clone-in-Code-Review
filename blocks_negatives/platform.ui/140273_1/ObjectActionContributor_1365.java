        String tag = element.getName();

        if (tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
            ((ObjectContribution) currentContribution)
                    .setVisibilityTest(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_FILTER)) {
            ((ObjectContribution) currentContribution).addFilterTest(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_ENABLEMENT)) {
            ((ObjectContribution) currentContribution)
                    .setEnablementTest(element);
            return true;
        }

        return super.readElement(element);
    }

    /**
     * Returns whether the current selection matches the contribution name filter.
     */
    private boolean testName(Object object) {
        String nameFilter = config.getAttribute(IWorkbenchRegistryConstants.ATT_NAME_FILTER);
        if (nameFilter == null) {
