		v.setColumnProperties(new String[] { "1", "2" });

		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				v, new CursorCellHighlighter(v, new TableCursor(v)));
		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(
				v) {
