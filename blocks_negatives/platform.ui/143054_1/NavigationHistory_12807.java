	    		if (perTabHistory.currentEntry != null) {
	    			final INavigationLocation location = perTabHistory.currentEntry.location;
	    			if (location!=null) {
	    				location.update();
	    			}
	    			perTabHistory.backwardEntries.addFirst(perTabHistory.currentEntry);
	    		}
	    		perTabHistory.currentEntry = newCurrent;
	            try {
	                ignoreEntries++;
	                if (newCurrent.editorInfo.memento != null) {
	                	newCurrent.editorInfo.restoreEditor();
	                	checkDuplicates(newCurrent.editorInfo);
	                }
	                newCurrent.restoreLocation();
	                updateActions();
	            } finally {
	            	ignoreEntries--;
	            }
	    	}
    	}
    }

    private void backwardForTab() {
    	Object editorTabCookie = getCookieForTab(page.getActiveEditor());
    	if (editorTabCookie!=null) {
	    	PerTabHistory perTabHistory = (PerTabHistory) perTabHistoryMap.get(editorTabCookie);
	    	if (perTabHistory != null && !perTabHistory.backwardEntries.isEmpty()) {
	    		NavigationHistoryEntry newCurrent = (NavigationHistoryEntry) perTabHistory.backwardEntries
	    				.removeFirst();
	    		if (perTabHistory.currentEntry != null) {
	    			perTabHistory.currentEntry.location.update();
	    			perTabHistory.forwardEntries.addFirst(perTabHistory.currentEntry);
	    		}
	    		perTabHistory.currentEntry = newCurrent;
	            try {
	                ignoreEntries++;
	                if (newCurrent.editorInfo.memento != null) {
	                	newCurrent.editorInfo.restoreEditor();
	                	checkDuplicates(newCurrent.editorInfo);
	                }
	                newCurrent.restoreLocation();
	                updateActions();
	            } finally {
	            	ignoreEntries--;
	            }
	    	}
    	}
    }

    private boolean hasEntriesForTab(boolean forward) {
    	Object editorTabCookie = getCookieForTab(page.getActiveEditor());
    	if (editorTabCookie!=null) {
	    	PerTabHistory perTabHistory = (PerTabHistory) perTabHistoryMap.get(editorTabCookie);
	    	if (perTabHistory != null) {
		    	LinkedList entries = forward ? perTabHistory.forwardEntries : perTabHistory.backwardEntries;
		    	return !entries.isEmpty();
	    	}
    	}
    	return false;
    }
