
		textEditor.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CTRL) {
					System.out.println("CTRL PRESSED"); //$NON-NLS-1$
				}

			}
		});
