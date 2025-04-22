		refresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			if (e.detail == SWT.ARROW) {
				refreshMenu.setVisible(true);
			} else {
				refresh();
			}
		}));
