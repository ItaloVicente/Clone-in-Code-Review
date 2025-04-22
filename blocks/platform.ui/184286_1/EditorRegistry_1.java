	}

	static Map<String, IEditorDescriptor> getIDtoOSEditors() {
		Map<String, IEditorDescriptor> map = new HashMap<>();
		IEditorDescriptor[] sortedEditorsFromOS = LazySortedEditorsFromOSHolder.INSTANCE;
		for (IEditorDescriptor desc : sortedEditorsFromOS) {
			map.put(desc.getId(), desc); // ignore duplicates
