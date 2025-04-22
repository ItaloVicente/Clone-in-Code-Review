		NavigationHistoryEntry e = getEntry(activeEntry);
		if (e == null)
			return false;
		IWorkbenchPartSite site = editor.getSite();
		if (site == null) // might happen if site has not being initialized yet
			return false;
		String editorID = site.getId();
		if (editorID == null) // should not happen for an editor
			return false;
		if (!editorID.equals(e.editorInfo.editorID))
			return false;
