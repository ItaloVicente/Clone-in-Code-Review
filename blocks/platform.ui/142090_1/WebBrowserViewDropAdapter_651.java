	private void doDropValidation(DropTargetEvent event) {
		if (event.detail != DND.DROP_NONE)
			lastValidOperation = event.detail;

		if (validateDrop(event.detail, event.currentDataType))
			currentOperation = lastValidOperation;
		else
			currentOperation = DND.DROP_NONE;

		event.detail = currentOperation;
	}

	@Override
