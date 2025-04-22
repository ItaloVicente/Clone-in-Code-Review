		StyledText commentWidget = commentViewer.getTextWidget();
		commentWidget.addCaretListener(event -> {
			Point location = commentWidget
					.getLocationAtOffset(event.caretOffset);
			scrollCommentAndDiff(location,
					commentWidget.getLineHeight(event.caretOffset));
		});

