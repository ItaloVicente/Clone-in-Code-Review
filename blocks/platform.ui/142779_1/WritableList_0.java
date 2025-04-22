		c.stream().map((element) -> wrappedList.indexOf(element)).filter((removeIndex) -> (removeIndex != -1)).forEachOrdered((removeIndex) -> {
			E removedElement = wrappedList.get(removeIndex);
			wrappedList.remove(removeIndex);
			entries.add(Diffs.createListDiffEntry(removeIndex, false,
				removedElement));
		});
