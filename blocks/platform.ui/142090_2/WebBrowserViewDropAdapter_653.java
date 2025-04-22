		if (!performDrop(event.data))
			event.detail = DND.DROP_NONE;

		currentOperation = event.detail;
	}

	@Override
