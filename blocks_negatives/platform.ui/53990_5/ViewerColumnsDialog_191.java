		Listener columnResize = new Listener() {
			@Override
			public void handleEvent(Event event) {
				column.setWidth(table.getClientArea().width);
			}
		};
