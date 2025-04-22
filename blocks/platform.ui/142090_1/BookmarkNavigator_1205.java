			}
		};
		viewer.addDragSupport(operations, transferTypes, listener);
	}

	void performDragSetData(DragSourceEvent event) {
		if (MarkerTransfer.getInstance().isSupportedType(event.dataType)) {
