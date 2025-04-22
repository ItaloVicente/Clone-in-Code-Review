		TableColumnLayout layout = new TableColumnLayout();
		tableContainer.setLayout(layout);
		TableColumn[] dynamicColumns = createColumns(rawTable, layout);
		rawTable.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				layout(rawTable, layout, dynamicColumns);
			}
		});
