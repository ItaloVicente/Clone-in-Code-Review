		table.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
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
					}
					break;
				case SWT.ARROW_DOWN:
					if (table.getSelectionIndex() == table.getItemCount() - 1) {
						fFilterText.setFocus();
					}
					break;
