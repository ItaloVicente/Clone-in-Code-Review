		commentAndDiffScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if (!resizing && commentViewer.getTextWidget()
						.getWordWrap()) {
					resizeCommentAndDiffScrolledComposite();
				}
			}
		});

