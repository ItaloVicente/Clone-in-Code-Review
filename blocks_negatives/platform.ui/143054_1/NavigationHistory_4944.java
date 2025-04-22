    public void updateCookieForTab(Object oldCookie, Object newCookie) {
    	if (newCookie.equals(oldCookie)) {
    		return;
    	}
    	PerTabHistory perTabHistory = (PerTabHistory) perTabHistoryMap.remove(oldCookie);
    	if (perTabHistory != null) {
    		perTabHistoryMap.put(newCookie, perTabHistory);
    	}
    }

    private void gotoEntryForTab(NavigationHistoryEntry target, boolean forward) {
    	Object editorTabCookie = getCookieForTab(page.getActiveEditor());
    	if (editorTabCookie!=null) {
	    	PerTabHistory perTabHistory = (PerTabHistory) perTabHistoryMap.get(editorTabCookie);
	    	if (perTabHistory != null) {
	    		LinkedList source = forward ? perTabHistory.forwardEntries : perTabHistory.backwardEntries;
	    		LinkedList destination = forward ? perTabHistory.backwardEntries : perTabHistory.forwardEntries;
