		IStructuredSelection structured = viewer.getStructuredSelection();
		Iterator<?> elements = structured.iterator();
		HashSet<IMarker> result = new HashSet<>();
		while (elements.hasNext()) {
			MarkerSupportItem next = (MarkerSupportItem) elements.next();
			if (next.isConcrete()) {
				result.add(((MarkerEntry) next).getMarker());
