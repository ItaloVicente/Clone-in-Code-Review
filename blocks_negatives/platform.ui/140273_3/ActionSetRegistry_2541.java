            }
        }
        mapPartToActionSets.clear();

    }

    /**
     * @param objects
     */
    private void removeActionSets(Object[] objects) {
        for (Object object : objects) {
            if (object instanceof IActionSetDescriptor) {
                IActionSetDescriptor desc = (IActionSetDescriptor) object;
                removeActionSet(desc);

                for (Iterator j = mapPartToActionSetIds.values().iterator(); j
                        .hasNext();) {
                    ArrayList list = (ArrayList) j.next();
                    list.remove(desc.getId());
                    if (list.isEmpty()) {
