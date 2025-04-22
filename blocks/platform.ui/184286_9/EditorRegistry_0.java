
		synchronized(this){
			mapIDtoOSEditors=null;
		}
	}

	private synchronized IEditorDescriptor getOSEditor(String id) {
		if (mapIDtoOSEditors == null) {
			mapIDtoOSEditors = new HashMap<>();
			IEditorDescriptor[] sortedEditorsFromOS = getSortedEditorsFromOS();
			for (IEditorDescriptor desc : sortedEditorsFromOS) {
				mapIDtoOSEditors.put(desc.getId(), desc); // ignore duplicates
			}
			WorkbenchPlugin.log("Initialized OS Editors for '" + id + "' returns:" + mapIDtoOSEditors.get(id)); //$NON-NLS-1$ //$NON-NLS-2$
