        }
    }

    /**
     * Returns whether all items in the list are checked.
     * This method is required, because this widget will keep items grey
     * checked even though all children are selected (see grayUpdateHierarchy()).
     * @return true if all items in the list are checked - false if not
     */
    public boolean isEveryItemChecked() {
    	Object[] children = treeContentProvider.getChildren(root);
        for (int i = 0; i < children.length; ++i) {
        	if (!whiteCheckedTreeItems.contains(children[i])) {
                if (!treeViewer.getGrayed(children[i]))
                	return false;
        		if (!isEveryChildrenChecked(children[i]))
        			return false;
        	}
        }
        return true;
    }
