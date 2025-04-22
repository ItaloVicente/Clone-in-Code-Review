		return new IOperationHistoryListener() {

			@Override
			public void historyNotification(OperationHistoryEvent event) {
				if (!event.getOperation().hasContext(
						WorkspaceUndoUtil.getWorkspaceUndoContext())) {
					return;
				}
				switch (event.getEventType()) {
				case OperationHistoryEvent.ABOUT_TO_EXECUTE:
				case OperationHistoryEvent.ABOUT_TO_UNDO:
				case OperationHistoryEvent.ABOUT_TO_REDO:
					operationInProgress = event.getOperation();
					break;
				case OperationHistoryEvent.DONE:
				case OperationHistoryEvent.UNDONE:
				case OperationHistoryEvent.REDONE:
					resetChangeCount();
					operationInProgress = null;
					break;
				case OperationHistoryEvent.OPERATION_NOT_OK:
					operationInProgress = null;
					break;
				}
