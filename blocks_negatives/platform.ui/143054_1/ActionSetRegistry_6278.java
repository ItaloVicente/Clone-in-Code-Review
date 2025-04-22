        }
        return null;
    }

    /**
     * Returns a list of the action sets known to the workbench.
     *
     * @return a list of action sets
     */
    public IActionSetDescriptor[] getActionSets() {
        return (IActionSetDescriptor []) children.toArray(new IActionSetDescriptor [children.size()]);
    }

    /**
     * Returns a list of the action sets associated with the given part id.
     *
     * @param partId the part id
     * @return a list of action sets
     */
    public IActionSetDescriptor[] getActionSetsFor(String partId) {
        ArrayList actionSets = (ArrayList) mapPartToActionSets.get(partId);
        if (actionSets != null) {
            return (IActionSetDescriptor[]) actionSets
                    .toArray(new IActionSetDescriptor[actionSets.size()]);
        }

        ArrayList actionSetIds = (ArrayList) mapPartToActionSetIds.get(partId);
        if (actionSetIds == null) {
