        NavigationHistoryEntry e = getEntry(activeEntry);
        if (e == null)
        	return false;
        IWorkbenchPartSite site = editor.getSite();
        	return false;
        String editorID = site.getId();
        	return false;
        if (!editorID.equals(e.editorInfo.editorID))
            return false;
