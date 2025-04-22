		branchTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TreeItem item = branchTree.getSelection()[0];
				if (item.getData() != null) {
					okPressed();
				} else { // expand element if it does not contains data
					item.setExpanded(!item.getExpanded());
				}
			}
		});
		branchTree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (SWT.ARROW_RIGHT == e.keyCode) { // expand three
					expandTreeElement(true);
				} else if (SWT.ARROW_LEFT == e.keyCode){ // collapse three
					expandTreeElement(false);
				}
			}
		});
