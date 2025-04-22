		String[] labels = { "Column 1", "Column 2", "Column 3" };
		for (String label : labels) {
			createColumnFor(viewer, label);
		}
		viewer.setInput(createModel());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
	}
