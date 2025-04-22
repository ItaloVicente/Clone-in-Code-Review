
		directoryCombo.addMouseListener(MouseListener.mouseUpAdapter(e -> {
			popupActivated = false;
			int caretPosition = ((Combo) e.getSource()).getCaretPosition();
			updateProposals(directoryCombo.getText().substring(0, caretPosition), popupActivated);
		}));
