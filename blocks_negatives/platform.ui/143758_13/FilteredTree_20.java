		if (useNewLook) {
			filterText.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					if (filterText.getText().equals(initialText)) {
						textChanged();
					}
