			} else if (element instanceof MPart) {
				MPart part = (MPart) element;
				unzoomSharedArea(part);

				if (CompatibilityEditor.MODEL_ELEMENT_ID.equals(part.getElementId())) {
					EditorReference reference = getEditorReference(part);
					if (reference != null) {
						reference.unsubscribe();
					}
