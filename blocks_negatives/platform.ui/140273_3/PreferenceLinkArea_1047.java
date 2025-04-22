    /**
     * Get the preference node with pageId.
     *
     * @param pageId
     * @return IPreferenceNode
     */
    private IPreferenceNode getPreferenceNode(String pageId) {
        Iterator iterator = PlatformUI.getWorkbench().getPreferenceManager()
                .getElements(PreferenceManager.PRE_ORDER).iterator();
        while (iterator.hasNext()) {
            IPreferenceNode next = (IPreferenceNode) iterator.next();
            if (next.getId().equals(pageId)) {
