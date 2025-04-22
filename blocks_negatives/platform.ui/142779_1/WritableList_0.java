		for (Object element : c) {
			int removeIndex = wrappedList.indexOf(element);
			if (removeIndex != -1) {
				E removedElement = wrappedList.get(removeIndex);
				wrappedList.remove(removeIndex);
				entries.add(Diffs.createListDiffEntry(removeIndex, false,
						removedElement));
			}
		}
