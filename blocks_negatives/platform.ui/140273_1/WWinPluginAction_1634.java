    /**
     * Creates any actions which belong to an activated plugin.
     */
    public static void refreshActionList() {
        Iterator iter = staticActionList.iterator();
        while (iter.hasNext()) {
            WWinPluginAction action = (WWinPluginAction) iter.next();
            if ((action.getDelegate() == null) && action.isOkToCreateDelegate()) {
                action.createDelegate();
            }
        }
    }
