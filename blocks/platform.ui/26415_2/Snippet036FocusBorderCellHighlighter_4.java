		String[] columLabels = { "Column 1", "Column 2", "Column 3" };
		for (String label : columLabels) {
			createColumnFor(v, label);
		}
		v.setInput(createModel());
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);
	}
