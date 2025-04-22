		List<EditorReference> refs;

		if (isEnableMRU()) {
			refs = page.getSortedEditorReferences();
		} else {
			refs = new ArrayList<>();
			for (IEditorReference ier : page.getEditorReferences()) {
				refs.add((EditorReference) ier);
			}
		}
