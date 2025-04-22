        settings.put(STORE_EXPANDED_CATEGORIES_ID, expandedCategoryIds);
        
        if (viewDescs.length > 0) {
            selectedViewId = viewDescs[0].getId();
        }
        settings.put(STORE_SELECTED_VIEW_ID, selectedViewId);
    }

    /**
     * Notifies that the selection has changed.
     * 
     * @param event event object describing the change
     */
    @Override
