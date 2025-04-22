            }
        }
    }

    /**
     *	Logically gray-check all ancestors of treeItem by ensuring that they
     *	appear in the checked table. Add any elements to the selectedNodes
     *  so we can track that has been done.
     */
    private void primeHierarchyForSelection(Object item, Set selectedNodes) {
        if (selectedNodes.contains(item)) {
