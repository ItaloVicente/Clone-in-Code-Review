
	static void update(ViewerCell cell, Consumer<ViewerCell> updater) {
		Object element = cell.getElement();
		if (element instanceof RepositoryNode
				|| element instanceof WorkingDirNode) {
			String textBefore = cell.getText();
			StyleRange[] rangesBefore = cell.getStyleRanges();
			updater.accept(cell);
			String textAfter = cell.getText();
			StyleRange[] rangesAfter = cell.getStyleRanges();
			if (textBefore.equals(textAfter)
					&& Arrays.equals(rangesBefore, rangesAfter)) {
				return;
			}
			Repository repository = ((RepositoryTreeNode<?>) element)
					.getRepository();
			if (repository == null) {
				return;
			}
			String newText = " - "; //$NON-NLS-1$
			if (element instanceof RepositoryNode) {
				newText += repository.getDirectory().getAbsolutePath();
			} else if (element instanceof WorkingDirNode) {
				newText += repository.getWorkTree().getAbsolutePath();
			}
			StyleRange style = new StyleRange();
			style.start = textAfter.length();
			style.length = newText.length();
			StyledString.QUALIFIER_STYLER.applyStyles(style);
			StyleRange[] newRanges = new StyleRange[rangesAfter.length + 1];
			System.arraycopy(rangesAfter, 0, newRanges, 0, rangesAfter.length);
			newRanges[newRanges.length - 1] = style;
			cell.setText(textAfter + newText);
			cell.setStyleRanges(newRanges);
		} else {
			updater.accept(cell);
		}
	}
