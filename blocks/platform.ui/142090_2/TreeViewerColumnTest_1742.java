		TreeViewer viewer = new TreeViewer(parent);
		viewer.setContentProvider(new TestModelContentProvider());
		viewer.setLabelProvider(new TableTreeTestLabelProvider());
		viewer.getTree().setLinesVisible(true);

		viewer.getTree().setHeaderVisible(true);
		String headers[] = { "column 1 header", "column 2 header" };

		final TreeColumn columns[] = new TreeColumn[headers.length];

		for (int i = 0; i < headers.length; i++) {
			TreeColumn tc = new TreeColumn(viewer.getTree(),
					SWT.NONE, i);
			tc.setResizable(true);
			tc.setText(headers[i]);
			tc.setWidth(25);
			columns[i] = tc;
		}
		fTreeViewer = viewer;
		return viewer;
	}

	@Override
