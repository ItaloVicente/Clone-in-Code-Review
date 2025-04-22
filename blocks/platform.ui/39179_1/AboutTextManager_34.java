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

		styledText.addMouseMoveListener(new MouseMoveListener() {
			@Override
