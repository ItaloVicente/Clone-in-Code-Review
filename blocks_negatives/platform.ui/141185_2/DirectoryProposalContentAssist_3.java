			if (e.keyCode == SWT.ESC) {
				popupActivated = false;
			}
			if (isTraverse(e)) {
				int caretPosition = directoryCombo.getCaretPosition();
				updateProposals(directoryCombo.getText().substring(0, caretPosition), popupActivated);
