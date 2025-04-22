            boolean checked = checkedStateStore.containsKey(currentElement);
            treeViewer.setChecked(currentElement, checked);
            treeViewer.setGrayed(currentElement, checked
                    && !whiteCheckedTreeItems.contains(currentElement));
        }
    }

    /**
     *	An item was checked in one of self's two views.  Determine which
     *	view this occurred in and delegate appropriately
     *
     *	@param event CheckStateChangedEvent
     */
    @Override
