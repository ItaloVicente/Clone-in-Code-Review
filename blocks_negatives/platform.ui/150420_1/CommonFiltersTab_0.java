		filterText.getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					@Override
					public void getName(AccessibleEvent e) {
						String filterTextString = filterText.getText();
						if (filterTextString.length() == 0) {
							e.result = initialFilterTextValue;
						} else {
							e.result = filterTextString;
						}
					}
				});

		filterText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (initialFilterTextValue.equals(filterText.getText().trim())) {
					filterText.selectAll();
				}
			}
		});

		filterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				super.mouseUp(e);
				if (initialFilterTextValue.equals(filterText.getText().trim())) {
					filterText.selectAll();
				}
			}
		});

