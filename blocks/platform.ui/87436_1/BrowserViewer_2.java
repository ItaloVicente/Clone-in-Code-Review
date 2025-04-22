		combo.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			try {
				if (combo.getSelectionIndex() != -1 && !combo.getListVisible()) {
					setURL(combo.getItem(combo.getSelectionIndex()));
				}
			} catch (Exception e1) {
			}
		}));
