        if (removeAllWhenShown) {
        	return true;
        }

        IContributionItem[] childItems = getItems();
        boolean visibleChildren = false;
        for (int j = 0; j < childItems.length; j++) {
            if (isChildVisible(childItems[j]) && !childItems[j].isSeparator()) {
                visibleChildren = true;
                break;
            }
        }

        return visibleChildren;
    }


    /**
     * The <code>MenuManager</code> implementation of this <code>ContributionManager</code> method
     * also propagates the dirty flag up the parent chain.
     *
     * @since 3.1
     */
    @Override
