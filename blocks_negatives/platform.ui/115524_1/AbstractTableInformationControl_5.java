		fFilterText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.CR:
				case SWT.KEYPAD_CR:
					gotoSelectedElement();
					break;
				case SWT.ARROW_DOWN:
					fTableViewer.getTable().setFocus();
					fTableViewer.getTable().setSelection(0);
					break;
				case SWT.ARROW_UP:
					fTableViewer.getTable().setFocus();
					fTableViewer.getTable().setSelection(
							fTableViewer.getTable().getItemCount() - 1);
					break;
				case SWT.ESC:
					dispose();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
