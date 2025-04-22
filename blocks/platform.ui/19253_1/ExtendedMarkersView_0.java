			final IStructuredSelection structured = (IStructuredSelection) selection;
			final List result = new ArrayList(structured.size());
			MarkerCategory lastCategory = null;
			for(Iterator i = structured.iterator(); i.hasNext();) {
				final MarkerSupportItem next = (MarkerSupportItem) i.next();
				if(next.isConcrete()) {
					if(lastCategory != null && lastCategory == next.getParent()) {
						continue;
					}
					result.add(next.getMarker());
