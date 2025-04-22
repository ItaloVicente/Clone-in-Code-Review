		if (ref != null) {
			for (IEditorReference reference : getEditorReferences()) {
				if (reference == ref) {
					hidePart(((EditorReference) ref).getModel(), true, true, false);
					break;
				}
			}
		}
