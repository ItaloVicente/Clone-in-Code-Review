
		TreeViewerColumn dateColumn = createColumn(layout,
				UIText.ReflogView_DateColumnHeader, 15, SWT.LEFT);
		dateColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				final PersonIdent who = entry.getWho();
				return absoluteFormatter.format(who.getWhen());
			}

			@Override
			public String getToolTipText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getNewId().name();
			}

			@Override
			public Image getImage(Object element) {
				return null;
			}

		});

