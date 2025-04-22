		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			Iterator<?> elements = structured.iterator();
			HashSet<IMarker> result = new HashSet<>();
			while (elements.hasNext()) {
				MarkerSupportItem next = (MarkerSupportItem) elements.next();
				if (next.isConcrete()) {
					result.add(((MarkerEntry) next).getMarker());
				}
			}
			if (result.isEmpty()) {
				return MarkerSupportInternalUtilities.EMPTY_MARKER_ARRAY;
