        checkedStateStore.put(treeElement, new ArrayList());
        if (determineShouldBeWhiteChecked(treeElement)) {
            setWhiteChecked(treeElement, true);
        }
        Object parent = treeContentProvider.getParent(treeElement);
        if (parent != null) {
