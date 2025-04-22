	private void setWrap(boolean wrap) {
		commentViewer.getTextWidget().setWordWrap(wrap);
		diffViewer.getTextWidget().setWordWrap(wrap);
		resizeCommentAndDiffScrolledComposite();
	}

	private void resizeCommentAndDiffScrolledComposite() {
		resizing = true;
		long start = 0;
		int lines = 0;
		if (trace) {
			IDocument document = diffViewer.getDocument();
			lines = document != null ? document.getNumberOfLines() : 0;
			if (lines > 1) {
						.printStackTrace(System.out);
			}
			start = System.currentTimeMillis();
		}

		Point size = commentAndDiffComposite
				.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		commentAndDiffComposite.layout();
		commentAndDiffScrolledComposite.setMinSize(size);
		resizing = false;

		if (trace) {
			long stop = System.currentTimeMillis();
			long time = stop - start;
			long lps = (lines * 1000) / (time + 1);
			System.out
					.println("Resize + diff: " + time + " ms, line/s: " + lps); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

