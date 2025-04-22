		diffWidget.addCaretListener(event -> {
			Point location = diffWidget.getLocationAtOffset(event.caretOffset);
			location.y += diffViewer.getControl().getLocation().y;
			scrollCommentAndDiff(location,
					diffWidget.getLineHeight(event.caretOffset));
		});

