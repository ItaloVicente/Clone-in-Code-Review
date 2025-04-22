			}
		} else if (element instanceof MPart) {
			MPart part = (MPart) element;
			unzoomSharedArea(part);

			if (CompatibilityEditor.MODEL_ELEMENT_ID.equals(part.getElementId())) {
				EditorReference reference2 = getEditorReference(part);
				if (reference2 != null) {
					reference2.unsubscribe();
