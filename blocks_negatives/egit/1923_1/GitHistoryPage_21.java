
	private class TextAction extends Action implements FocusListener,
			ISelectionChangedListener {
		private final int op;

		TextAction(final int operationCode) {
			op = operationCode;
			setEnabled(false);
		}

		public void run() {
			if (commentViewer.getTextWidget().isFocusControl()) {
				if (commentViewer.canDoOperation(op))
					commentViewer.doOperation(op);
			} else if (fileViewer.getTable().isFocusControl()) {
				switch (op) {
				case ITextOperationTarget.COPY:
					fileViewer.doCopy();
					break;
				case ITextOperationTarget.SELECT_ALL:
					fileViewer.doSelectAll();
					break;
				}
			} else if (graph.getControl().isFocusControl()) {
				switch (op) {
				case ITextOperationTarget.COPY:
					graph.doCopy();
					break;
				}
			}
		}

		private void update() {
			if (commentViewer.getTextWidget().isFocusControl()) {
				setEnabled(commentViewer.canDoOperation(op));
			} else if (fileViewer.getTable().isFocusControl()) {
				switch (op) {
				case ITextOperationTarget.COPY:
					setEnabled(!fileViewer.getSelection().isEmpty());
					break;
				case ITextOperationTarget.SELECT_ALL:
					setEnabled(fileViewer.getTable().getItemCount() > 0);
					break;
				}
			} else if (graph.getControl().isFocusControl()) {
				switch (op) {
				case ITextOperationTarget.COPY:
					setEnabled(graph.canDoCopy());
					break;
				case ITextOperationTarget.SELECT_ALL:
					setEnabled(false);
					break;
				}
			}
		}

		public void focusGained(final FocusEvent e) {
			update();
		}

		public void selectionChanged(final SelectionChangedEvent event) {
			update();
		}

		public void focusLost(final FocusEvent e) {
		}
	}
