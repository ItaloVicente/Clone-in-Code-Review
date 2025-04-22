	@Override
	protected IVerticalRulerColumn createLineNumberRulerColumn() {
		lineNumberColumn = new OldNewLogicalLineNumberRulerColumn(
				plainLineNumbers);
		initializeLineNumberRulerColumn(lineNumberColumn);
		return lineNumberColumn;
	}

