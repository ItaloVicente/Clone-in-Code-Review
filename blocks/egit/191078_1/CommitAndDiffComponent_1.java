		Point oldSize = commentAndDiffComposite.getSize();
		Rectangle minSize = commentAndDiffScrolledComposite.getClientArea();
		Point size;
		StyledText text = commentViewer.getTextWidget();
		if (text == null) {
			size = new Point(0, 0);
		} else if (text.getWordWrap()) {
			size = commentAndDiffComposite.computeSize(minSize.width,
					SWT.DEFAULT);
		} else {
			size = commentAndDiffComposite.computeSize(SWT.DEFAULT,
