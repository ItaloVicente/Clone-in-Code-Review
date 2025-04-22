		if (isPerTabHistoryEnabled()) {
			markLocationForTab(part);
		}
		INavigationLocation location = null;
		if (part instanceof INavigationLocationProvider) {
			location = ((INavigationLocationProvider) part).createNavigationLocation();
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
		printEntries("added entry"); //$NON-NLS-1$
		updateActions();
	}

	private void printEntries(String label) {
		if (DEBUG) {
			System.out.println("+++++ " + label + "+++++ "); //$NON-NLS-1$ //$NON-NLS-2$
			int size = history.size();
			for (int i = 0; i < size; i++) {
				String append = activeEntry == i ? ">>" : ""; //$NON-NLS-1$ //$NON-NLS-2$
				System.out.println(append + "Index: " + i + " " + history.get(i)); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

		if (isPerTabHistoryEnabled()) {
			return hasEntriesForTab(true);
		}
		return (0 <= activeEntry + 1) && (activeEntry + 1 < history.size());
	}

		if (isPerTabHistoryEnabled()) {
			return hasEntriesForTab(false);
		}
		return (0 <= activeEntry - 1) && (activeEntry - 1 < history.size());
	}

	private void updateActions() {
		if (backwardAction != null) {
