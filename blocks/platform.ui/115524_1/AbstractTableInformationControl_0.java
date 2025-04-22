		table.addKeyListener(KeyListener.keyPressedAdapter(e -> {
			switch (e.keyCode) {
			case SWT.ESC:
				dispose();
				break;
			case SWT.DEL:
				removeSelectedItem(null);
				e.character = SWT.NONE;
				e.doit = false;
				break;
			case SWT.ARROW_UP:
				if (table.getSelectionIndex() == 0) {
					fFilterText.setFocus();
