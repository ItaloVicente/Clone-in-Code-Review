		specViewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					doDeleteRefSpecs();
				}
			}
		});

