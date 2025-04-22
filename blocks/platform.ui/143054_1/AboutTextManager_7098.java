				mouseDown = false;
				int offset = styledText.getCaretOffset();
				if (dragEvent) {
					dragEvent = false;
					if (item != null && item.isLinkAt(offset)) {
						styledText.setCursor(handCursor);
					}
				} else if (item != null && item.isLinkAt(offset)) {
					styledText.setCursor(busyCursor);
					AboutUtils.openLink(styledText.getShell(), item.getLinkAt(offset));
					StyleRange selectionRange = getCurrentRange();
					styledText.setSelectionRange(selectionRange.start, selectionRange.length);
					styledText.setCursor(null);
				}
			}
		});

		styledText.addMouseMoveListener(e -> {
			if (mouseDown) {
				if (!dragEvent) {
					StyledText text1 = (StyledText) e.widget;
					text1.setCursor(null);
				}
				dragEvent = true;
				return;
			}
			StyledText text2 = (StyledText) e.widget;
