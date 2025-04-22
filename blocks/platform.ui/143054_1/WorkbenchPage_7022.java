		} else if (element instanceof MPart && newValue == null) {
			MPart changedPart = (MPart) element;
			Object impl = changedPart.getObject();
			if (impl != null && !(impl instanceof CompatibilityPart)) {
				EditorReference eRef = getEditorReference(changedPart);
				if (eRef != null)
					editorReferences.remove(eRef);
				ViewReference vRef = getViewReference(changedPart);
				if (vRef != null)
					viewReferences.remove(vRef);
			}
