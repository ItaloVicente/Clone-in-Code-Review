        if (isPerTabHistoryEnabled()) {
        	markLocationForTab(part);
        }
        INavigationLocation location = null;
        if (part instanceof INavigationLocationProvider) {
			location = ((INavigationLocationProvider) part)
                    .createNavigationLocation();
		}

        NavigationHistoryEntry current = getEntry(activeEntry);
        if (current != null && current.editorInfo.memento != null) {
            current.editorInfo.restoreEditor();
            checkDuplicates(current.editorInfo);
        }
        NavigationHistoryEntry e = createEntry(page, part, location);
        if (current == null) {
            add(e);
        } else {
            if (e.mergeInto(current)) {
                disposeEntry(e);
                removeForwardEntries();
            } else {
                add(e);
            }
        }
        updateActions();
    }

    /*
     * Prints all the entries in the console. For debug only.
     */
    private void printEntries(String label) {
        if (DEBUG) {
            int size = history.size();
            for (int i = 0; i < size; i++) {
                System.out.println(append
            }
        }
    }

    /*
     * Returns true if the forward action can be performed otherwise returns false.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    /* package */boolean canForward() {
    	if (isPerTabHistoryEnabled()) {
    		return hasEntriesForTab(true);
    	}
        return (0 <= activeEntry + 1) && (activeEntry + 1 < history.size());
    }

    /*
     * Returns true if the backward action can be performed otherwise returns false.
     * <p>
     * (Called by NavigationHistoryAction)
     * </p>
     */
    /* package */boolean canBackward() {
    	if (isPerTabHistoryEnabled()) {
    		return hasEntriesForTab(false);
    	}
        return (0 <= activeEntry - 1) && (activeEntry - 1 < history.size());
    }

    /*
     * Update the actions enable/disable and tooltip state.
     */
    private void updateActions() {
        if (backwardAction != null) {
