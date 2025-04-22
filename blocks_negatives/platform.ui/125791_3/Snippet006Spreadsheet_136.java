			});
			cursor.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == SWT.MOD1 || e.keyCode == SWT.MOD2 || (e.stateMask & SWT.MOD1) != 0
							|| (e.stateMask & SWT.MOD2) != 0) {
						cursor.setVisible(false);
