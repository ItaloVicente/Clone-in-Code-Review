     * Adds an association between an action set an a part.
     */
    private Object addAssociation(String actionSetId, String partId) {
        ArrayList actionSets = (ArrayList) mapPartToActionSetIds.get(partId);
        if (actionSets == null) {
            actionSets = new ArrayList();
            mapPartToActionSetIds.put(partId, actionSets);
        }
        actionSets.add(actionSetId);

        ActionSetPartAssociation association = new ActionSetPartAssociation(partId, actionSetId);
        return association;
    }

    /**
     * Finds and returns the registered action set with the given id.
     *
     * @param id the action set id
     * @return the action set, or <code>null</code> if none
     * @see IActionSetDescriptor#getId
     */
    public IActionSetDescriptor findActionSet(String id) {
        Iterator i = children.iterator();
        while (i.hasNext()) {
            IActionSetDescriptor desc = (IActionSetDescriptor) i.next();
            if (desc.getId().equals(id)) {
