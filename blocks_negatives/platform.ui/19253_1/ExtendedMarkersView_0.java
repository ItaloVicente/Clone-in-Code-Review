			IStructuredSelection structured = (IStructuredSelection) selection;
			Iterator elements = structured.iterator();
			Collection result = new HashSet();
			while (elements.hasNext()) {
				MarkerSupportItem next = (MarkerSupportItem) elements.next();
				if (next.isConcrete()) {
					result.add(((MarkerEntry) next).getMarker());
