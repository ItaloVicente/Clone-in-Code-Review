        }
        return false;
    }

    /**
     */
    public IActionSet[] getActionSets() {
        Collection setRecCollection = mapDescToRec.values();
        IActionSet result[] = new IActionSet[setRecCollection.size()];
        int i = 0;
        for (Iterator iterator = setRecCollection.iterator(); iterator
                .hasNext(); i++) {
