	private List<IEditorDescriptor> getEditorDescriptors(IMemento[] children,
			Map<String, IEditorDescriptor> editorTable) {
		if (children == null || children.length == 0) {
			return Collections.emptyList();
		}
		List<IEditorDescriptor> res = new ArrayList<>(children.length);
		for (IMemento child : children) {
			String editorId = child.getString(IWorkbenchConstants.TAG_ID);
			if (editorId != null && editorTable.containsKey(editorId)) {
				res.add(editorTable.get(editorId));
			}
		}
		return res;
	}

