		Information information = null;
		ViewerCell cell = tableViewer.getCell(new Point(e.x, e.y));
		if (cell != null) {
			SWTCommit commit = (SWTCommit) cell.getElement();
			if (commit != null)
				information = computeInformationForCommit(commit, cell, e);
