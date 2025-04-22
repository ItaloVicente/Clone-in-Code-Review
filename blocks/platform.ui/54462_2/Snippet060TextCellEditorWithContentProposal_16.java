		FocusCellHighlighter focusCellHighlighter = new FocusCellOwnerDrawHighlighter(
				viewer);
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				viewer, focusCellHighlighter);

		TableViewerEditor.create(viewer, focusCellManager, activationSupport,
				ColumnViewerEditor.TABBING_VERTICAL
						| ColumnViewerEditor.KEYBOARD_ACTIVATION);
