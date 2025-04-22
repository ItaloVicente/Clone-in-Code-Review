		ColumnViewerToolTipSupport.enableFor(reflogTableViewer);

		TableViewerColumn fromColum = createColumn(layout, "From", 10, SWT.LEFT); //$NON-NLS-1$
		fromColum.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getOldId().abbreviate(6).name();
			}

			@Override
			public String getToolTipText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getOldId().name();
			}

			@Override
			public Image getImage(Object element) {
				return UIIcons.BRANCH.createImage();
			}
		});
		TableViewerColumn toColumn = createColumn(layout, "To", 10, SWT.LEFT); //$NON-NLS-1$
		toColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getNewId().abbreviate(6).name();
			}

			@Override
			public String getToolTipText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getNewId().name();
			}

			@Override
			public Image getImage(Object element) {
				return UIIcons.BRANCH.createImage();
			}

		});
		TableViewerColumn messageColumn = createColumn(layout, "Message", 50, SWT.LEFT); //$NON-NLS-1$
		messageColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getComment();
			}
		});
