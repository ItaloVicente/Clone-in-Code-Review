            checkedStateStore.put(key, selections);
            selectedNodes.add(key);
            Object parent = treeContentProvider.getParent(key);
            if (parent != null) {
                primeHierarchyForSelection(parent, selectedNodes);
            }
        }

        treeViewer.setCheckedElements(checkedStateStore.keySet().toArray());
        treeViewer.setGrayedElements(checkedStateStore.keySet().toArray());

        if (currentTreeSelection != null) {
            Object displayItems = items.get(currentTreeSelection);
            if (displayItems != null) {
