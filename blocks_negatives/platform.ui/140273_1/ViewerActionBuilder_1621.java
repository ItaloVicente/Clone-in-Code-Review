        String tag = element.getName();

        if (currentContribution != null && tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
            ((ViewerContribution) currentContribution)
                    .setVisibilityTest(element);
            return true;
        }

        return super.readElement(element);
    }

    /**
     * Reads the contributions for a viewer menu.
     * This method is typically used in conjunction with <code>contribute</code> to read
     * and then insert actions for a particular viewer menu.
     *
     * @param id the menu id
     * @param prov the selection provider for the control containing the menu
     * @param part the part containing the menu.
     * @return <code>true</code> if 1 or more items were read.
     */
    public boolean readViewerContributions(String id, ISelectionProvider prov,
            IWorkbenchPart part) {
