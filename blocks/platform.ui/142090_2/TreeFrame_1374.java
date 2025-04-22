			if (expandedElements.length > 0) {
				IMemento expandedMem = memento.createChild(TAG_EXPANDED);
				saveElements(expandedElements, expandedMem);
			}
			if (selection instanceof IStructuredSelection) {
				Object[] elements = ((IStructuredSelection) selection)
						.toArray();
				if (elements.length > 0) {
					IMemento selectionMem = memento.createChild(TAG_SELECTION);
					saveElements(elements, selectionMem);
				}
			}
		}
	}
