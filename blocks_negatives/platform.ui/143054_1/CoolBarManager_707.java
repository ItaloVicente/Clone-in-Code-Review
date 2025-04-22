        if (!coolBarExist()) {
            return false;
        }
        return coolBar.getLocked();
    }

    /**
     * Returns the number of rows that should be displayed visually.
     *
     * @param items
     *            the array of contributin items
     * @return the number of rows
     */
    private int getNumRows(IContributionItem[] items) {
        int numRows = 1;
        boolean separatorFound = false;
        for (int i = 0; i < items.length; i++) {
            if (items[i].isSeparator()) {
                separatorFound = true;
            }
            if ((separatorFound) && (isChildVisible(items[i]))
                    && (!items[i].isGroupMarker()) && (!items[i].isSeparator())) {
                numRows++;
                separatorFound = false;
            }
        }
        return numRows;
    }

    @Override
