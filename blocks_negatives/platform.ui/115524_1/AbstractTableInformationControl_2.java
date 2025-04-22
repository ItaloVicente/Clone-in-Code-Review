		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (table.getSelectionCount() < 1) {
					return;
				}
