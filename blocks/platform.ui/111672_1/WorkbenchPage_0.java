		IEditorRegistry reg = getWorkbenchWindow().getWorkbench().getEditorRegistry();
		IEditorDescriptor desc = reg.findEditor(editorId);
		if (desc == null && reg instanceof EditorRegistry) {
			for (IEditorDescriptor ied : ((EditorRegistry) reg).getSortedEditorsFromOS()) {
				if (editorId.equals(ied.getId())) {
					desc = ied;
					break;
				}
			}
		}
