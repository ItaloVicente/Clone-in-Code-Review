		if (saveable2Processed) {
			listIterator = dirtyParts.listIterator();
			while (listIterator.hasNext()) {
					ISaveablePart part = (ISaveablePart) listIterator.next();
				if (!part.isDirty()) {
					listIterator.remove();
