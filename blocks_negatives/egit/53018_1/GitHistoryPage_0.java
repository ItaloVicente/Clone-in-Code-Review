		int widthHint;
		if (commentViewer.getTextWidget().getWordWrap()) {
			widthHint = commentAndDiffScrolledComposite.getClientArea().width;
			if (commentAndDiffScrolledComposite.getVerticalBar() != null
					&& !commentAndDiffScrolledComposite.getVerticalBar().isVisible())
				widthHint -= commentAndDiffScrolledComposite.getVerticalBar().getSize().x;
		} else {
			widthHint = SWT.DEFAULT;
		}
