
		synchronized(this){
			mapIDtoOSEditors=null;
		}
	}

	private synchronized Map<String, IEditorDescriptor> getIDtoOSEditors() {
		if (mapIDtoOSEditors == null) {
			mapIDtoOSEditors = new HashMap<>();
			IEditorDescriptor[] sortedEditorsFromOS = getSortedEditorsFromOS();
			for (IEditorDescriptor desc : sortedEditorsFromOS) {
				mapIDtoOSEditors.put(desc.getId(), desc); // ignore duplicates
			}
