		commentAndDiffScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if (commentViewer.getTextWidget().getWordWrap())
					resizeCommentAndDiffScrolledComposite();
			}
		});
