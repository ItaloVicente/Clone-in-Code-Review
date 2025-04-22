    final DelegatingStyledCellLabelProvider<File> styledCellLP1 = new DelegatingStyledCellLabelProvider<File>(
				new NameAndSizeLabelProvider());
    final DelegatingStyledCellLabelProvider<File> styledCellLP2 = new DelegatingStyledCellLabelProvider<File>(
				new ModifiedDateLabelProvider());
		final ColumnViewer<File, FileSystemRoot> ownerDrawViewer = createViewer(
				"Owner draw viewer:", composite, styledCellLP1, styledCellLP2); //$NON-NLS-1$
