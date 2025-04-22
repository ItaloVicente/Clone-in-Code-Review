		if (saveable2Processed) {
			listIterator = dirtyParts.listIterator();
			while (listIterator.hasNext()) {
				ISaveablePart part = listIterator.next();
				if (!part.isDirty()) {
					listIterator.remove();
