		IStructuredSelection structured = viewer.getStructuredSelection();
		final List<IMarker> result = new ArrayList<>(structured.size());
		MarkerCategory lastCategory = null;
		for (Iterator<?> i = structured.iterator(); i.hasNext();) {
			final MarkerSupportItem next = (MarkerSupportItem) i.next();
			if (next.isConcrete()) {
				if (lastCategory != null && lastCategory == next.getParent()) {
					continue;
				}
				result.add(next.getMarker());
			} else {
				lastCategory = (MarkerCategory) next;
				final MarkerEntry[] children = (MarkerEntry[]) lastCategory.getChildren();
