		if (persistable != null) {
			IMemento frameMemento = memento.createChild(TAG_FRAME_INPUT);

			frameMemento.putString(TAG_FACTORY_ID, persistable.getFactoryId());
			persistable.saveState(frameMemento);

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

	public void setInput(Object input) {
		this.input = input;
	}

	public void setExpandedElements(Object[] expandedElements) {
		this.expandedElements = expandedElements;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}
