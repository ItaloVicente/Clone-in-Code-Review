    }

    /**
     * Update the tree to only select those elements that match the selected types.
     * Do nothing by default.
     */
    protected void setupSelectionsBasedOnSelectedTypes() {
    }

    /**
     * Update the selections with those in map .
     * @param map Map - key tree elements, values Lists of list elements
     */
    protected void updateSelections(final Map map) {

        Runnable runnable = () -> selectionGroup.updateSelections(map);

        BusyIndicator.showWhile(getShell().getDisplay(), runnable);
    }

    /**
     * Check if widgets are enabled or disabled by a change in the dialog.
     */
    @Override
