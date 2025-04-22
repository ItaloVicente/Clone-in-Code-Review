
		selectionContains(selectedElements, oldElement).ifPresent(iter -> {
			iter.remove();
			selectedElements.add(newElement);
			viewer.setSelection(new StructuredSelection(selectedElements));
		});
