		resizing = true;
		long start = 0;
		int lines = 0;
		if (trace) {
			IDocument document = diffViewer.getDocument();
			lines = document != null ? document.getNumberOfLines() : 0;
			System.out.println("Lines: " + lines); //$NON-NLS-1$
			if (lines > 1) {
				new Exception("resizeCommentAndDiffScrolledComposite") //$NON-NLS-1$
						.printStackTrace(System.out);
			}
			start = System.currentTimeMillis();
		}

