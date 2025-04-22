	private IEditorDescriptor[] getAssociatedEditors() {
		Table editorTable = editorAssociationsViewer.getTable();
		if (editorTable == null) {
			return null;
		}
		if (editorTable.getItemCount() > 0) {
			ArrayList<IEditorDescriptor> editorList = new ArrayList<>();
			for (int i = 0; i < editorTable.getItemCount(); i++) {
				editorList.add((IEditorDescriptor) editorTable.getItem(i).getData());
			}

			return editorList.toArray(new IEditorDescriptor[editorList.size()]);
		}
		return null;
	}

