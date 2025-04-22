    protected void doCheckStateChanged(Object element) {
        Widget item = findItem(element);
        if (item instanceof TreeItem) {
            TreeItem treeItem = (TreeItem) item;
            treeItem.setGrayed(false);
            updateChildrenItems(treeItem);
            updateParentItems(treeItem.getParentItem());
        }
    }

    /**
     * The item has expanded. Updates the checked state of its children.
     */
    private void initializeItem(TreeItem item) {
        if (item.getChecked() && !item.getGrayed()) {
            updateChildrenItems(item);
        }
    }

    /**
     * Updates the check state of all created children
     */
    private void updateChildrenItems(TreeItem parent) {
        Item[] children = getChildren(parent);
        boolean state = parent.getChecked();
        for (Item element : children) {
            TreeItem curr = (TreeItem) element;
            if (curr.getData() != null
                    && ((curr.getChecked() != state) || curr.getGrayed())) {
                curr.setChecked(state);
                curr.setGrayed(false);
                updateChildrenItems(curr);
            }
        }
    }

    /**
     * Updates the check / gray state of all parent items
     */
    private void updateParentItems(TreeItem item) {
        if (item != null) {
            Item[] children = getChildren(item);
            boolean containsChecked = false;
            boolean containsUnchecked = false;
            for (Item element : children) {
                TreeItem curr = (TreeItem) element;
                containsChecked |= curr.getChecked();
                containsUnchecked |= (!curr.getChecked() || curr.getGrayed());
            }
            item.setChecked(containsChecked);
            item.setGrayed(containsChecked && containsUnchecked);
            updateParentItems(item.getParentItem());
        }
    }


    @Override
	public boolean setChecked(Object element, boolean state) {
        if (super.setChecked(element, state)) {
            doCheckStateChanged(element);
            return true;
        }
        return false;
    }
