			}
		};
		DragSource dragSource = new DragSource(
				viewer.getControl(), operations);
		dragSource.setTransfer(transferTypes);
		dragSource.addDragListener(listener);
	}

	void performDragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (selection.isEmpty()) {
