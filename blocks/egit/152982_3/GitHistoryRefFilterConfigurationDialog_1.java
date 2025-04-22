			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				boolean singleSelect = configsTable.getStructuredSelection()
						.size() == 1;
				boolean isLeftDoubleClick = event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						&& ((MouseEvent) event.sourceEvent).button == 1;
				return singleSelect && (isLeftDoubleClick
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
