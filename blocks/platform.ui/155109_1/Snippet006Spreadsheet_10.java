			}
		});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.MOD1 && (e.stateMask & SWT.MOD2) != 0) {
					return;
