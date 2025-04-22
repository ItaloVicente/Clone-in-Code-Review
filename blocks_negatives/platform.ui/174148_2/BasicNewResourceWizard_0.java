		List<IWorkbenchPart> parts = new ArrayList<>();
		for (IWorkbenchPartReference ref : page.getViewReferences()) {
			IWorkbenchPart part = ref.getPart(false);
			if (part != null) {
				parts.add(part);
			}
		}
		for (IWorkbenchPartReference ref : page.getEditorReferences()) {
			if (ref.getPart(false) != null) {
				parts.add(ref.getPart(false));
			}
		}
