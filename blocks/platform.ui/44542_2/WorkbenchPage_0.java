		if (ref != null) {
			for (IEditorReference reference : getEditorReferences()) {
				if (reference == ref) {
					activate(((EditorReference) ref).getPart(true));
					break;
				}
			}
		}
