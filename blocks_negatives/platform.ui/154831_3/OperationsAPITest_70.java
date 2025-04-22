		listener = new IOperationHistoryListener() {
			@Override
			public void historyNotification(OperationHistoryEvent event) {
				switch (event.getEventType()) {
				case OperationHistoryEvent.ABOUT_TO_EXECUTE:
					preExec++;
					break;
				case OperationHistoryEvent.ABOUT_TO_UNDO:
					preUndo++;
					break;
				case OperationHistoryEvent.ABOUT_TO_REDO:
					preRedo++;
					break;
				case OperationHistoryEvent.DONE:
					postExec++;
					break;
				case OperationHistoryEvent.UNDONE:
					postUndo++;
					break;
				case OperationHistoryEvent.REDONE:
					postRedo++;
					break;
				case OperationHistoryEvent.OPERATION_ADDED:
					add++;
					break;
				case OperationHistoryEvent.OPERATION_REMOVED:
					remove++;
					break;
				case OperationHistoryEvent.OPERATION_NOT_OK:
					notOK++;
					break;
				case OperationHistoryEvent.OPERATION_CHANGED:
					changed++;
					break;
				}
