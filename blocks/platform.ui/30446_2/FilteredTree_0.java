		filterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (filterText.getText().equals(initialText)) {
					setFilterText(""); //$NON-NLS-1$
					textChanged();
