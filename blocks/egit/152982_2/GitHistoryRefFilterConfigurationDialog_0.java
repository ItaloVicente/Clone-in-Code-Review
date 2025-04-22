		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
				configsTable, new FocusCellHighlighter(configsTable) {
				});
		ColumnViewerEditorActivationStrategy editorActivation = new ColumnViewerEditorActivationStrategy(
				configsTable) {

