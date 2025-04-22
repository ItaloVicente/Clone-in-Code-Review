		currentEvent = event;
		if (!validateDrop(currentTarget, event.detail, event.currentDataType)) {
			currentOperation = event.detail = DND.DROP_NONE;
		}
		currentEvent = null;
	}

	protected Rectangle getBounds(Item item) {
		if (item instanceof TreeItem) {
			return ((TreeItem) item).getBounds();
		}
		if (item instanceof TableItem) {
			return ((TableItem) item).getBounds(0);
		}
		return null;
	}

	protected int getCurrentLocation() {
		return currentLocation;
	}

	protected int getCurrentOperation() {
		return currentOperation;
	}

	protected Object getCurrentTarget() {
		return currentTarget;
	}

	protected DropTargetEvent getCurrentEvent() {
		Assert.isTrue(currentEvent != null);
		return currentEvent;
	}

	public boolean getFeedbackEnabled() {
		return feedbackEnabled;
	}
