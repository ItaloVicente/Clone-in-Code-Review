		diffViewer = new DiffViewer(commentAndDiffComposite, null, SWT.NONE, false);
		diffViewer.getControl().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).create());
		diffViewer.setEditable(false);

		setWrap(store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP));

		commentAndDiffScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if (commentViewer.getTextWidget().getWordWrap())
					resizeCommentAndDiffScrolledComposite();
			}
		});

