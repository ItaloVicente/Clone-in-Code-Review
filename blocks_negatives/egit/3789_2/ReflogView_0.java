		reflogTableViewer = new TableViewer(toolkit.createTable(tableComposite,
				SWT.FULL_SELECTION | SWT.MULTI));
		reflogTableViewer.getTable().setLinesVisible(true);
		reflogTableViewer.getTable().setHeaderVisible(true);
		reflogTableViewer.setContentProvider(new ReflogViewContentProvider());
		ColumnViewerToolTipSupport.enableFor(reflogTableViewer);
